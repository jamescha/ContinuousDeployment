package pelco.vxst.continuousdeployment.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import pelco.vxst.continuousdeployment.shared.Role;
import pelco.vxst.continuousdeployment.shared.User;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ButtonCell;
import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.ControlLabel;
import com.github.gwtbootstrap.client.ui.Form;
import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.Row;
import com.github.gwtbootstrap.client.ui.SimplePager;
import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.base.TextBox;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class UserUI extends Composite {

	private static UserUiBinder uiBinder = GWT.create(UserUiBinder.class);
	
	interface UserUiBinder extends UiBinder<Widget, UserUI> {
	}
	
	@UiField
	Nav formNav;
	@UiField
	Row dupError;
	@UiField
	SimplePager userPager;
	@UiField
	CellTable<User> userCellTable;
	@UiField
	Form userForm;
	
	private List<User> userList;
	private TextBox usernameBox = new TextBox();
	private TextArea roleBox = new TextArea();
	
	private final EnduraSoapServiceAsync enduraSoapService = GWT
			.create(EnduraSoapService.class);

	public UserUI() {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		final FormPanel fileForm = new FormPanel();
		final HorizontalPanel horFormPanel = new HorizontalPanel();
		final FileUpload fileUpload = new FileUpload();
		final Button fileSubmit = new Button();
		
		fileForm.setAction(GWT.getModuleBaseURL() + "upload");
		
		formNav.add(fileForm);
		
		usernameBox.setText("");
		usernameBox.setTitle("User");
		roleBox.setText("");
		roleBox.setEnabled(false);
		usernameBox.setEnabled(false);
		userForm.add(new ControlLabel("UserName"));
		userForm.add(usernameBox);
		userForm.add(roleBox);
		
		//File Upload SetUP
		fileForm.setMethod(FormPanel.METHOD_POST);
		fileForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		fileUpload.setName("userFile");
		
		fileSubmit.setText("Submit");
		fileSubmit.setType(ButtonType.WARNING);
		fileSubmit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				fileForm.submit();
			}
		});

		fileForm.addSubmitHandler(new FormPanel.SubmitHandler() {
			
			@Override
			public void onSubmit(SubmitEvent event) {
				if (fileUpload.getFilename().length() == 0) {
					Window.alert("Please Select a File");
					event.cancel();
				}
			}
		});
		
		fileForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				Window.alert(event.getResults());
				loadUsers();
			}
		});
		
		horFormPanel.add(fileUpload);
		horFormPanel.add(fileSubmit);
		fileForm.setWidget(horFormPanel);
		
		//Set Pager
		userPager.setDisplay(userCellTable);
		ListDataProvider<User> dataProvider = new ListDataProvider<User>();
		dataProvider.addDataDisplay(userCellTable);
		userList = dataProvider.getList();
		
		ListHandler<User> sortHandler = new ListHandler<User>(userList);
		userCellTable.addColumnSortHandler(sortHandler);
				
		Column<User,String> nameColumn = new Column<User, String>(new ClickableTextCell()) {
			@Override
			public String getValue(User user) {
				return user.getName();
			}
		};
		
		nameColumn.setSortable(true);
		sortHandler.setComparator(nameColumn, new Comparator<User>() {
			@Override
			public int compare(User user1, User user2) {
				return user1.getName().compareTo(user2.getName());
			}
		});
		
		nameColumn.setFieldUpdater(new FieldUpdater<User, String>() {
			@Override
			public void update(int index, User user, String value) {
				usernameBox.setText(value);
				enduraSoapService.userGetRoles(user.getDbId(), new AsyncCallback<ArrayList<Role>>() {
					
					@Override
					public void onSuccess(ArrayList<Role> result) {
						String temp = "";
						for (Role role : result) {
							temp = temp + role.getName() + "\n";
						}
						if (result.size() == 0) {
							temp = "No Roles";
						}
						roleBox.setText(temp);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						roleBox.setText("");
					}
				});
			}
		});
		
		ButtonCell deleteButton = new ButtonCell(ButtonType.DANGER);
		Column<User, String> delete = new Column<User, String> (deleteButton){
			@Override
			public String getValue(User object) {
				return "Delete";
			}
		};
		
		delete.setFieldUpdater(new FieldUpdater<User, String>() {
			
			@Override
			public void update(int index, final User user, String value) {
				userList.remove(index);
				userCellTable.setRowCount(userList.size());
				userCellTable.setRowData(0, userList);
				
				enduraSoapService.userDelete(user.getDbId().toString(), "366", new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						System.out.println(user.getName() + " has been deleted!");
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}
				});
			}
		});
		
		userCellTable.addColumn(nameColumn,"Name");
		userCellTable.addColumn(delete, "Delete");
		userCellTable.setPageSize(10);
		
		dataProvider.refresh();
		
		}
	
	private Boolean checkDup (final User user) {
		for (User person: userList) {
			if (person.getName() == user.getName())
				return true;
		}
		return false;
	}
	
	public void setUrl(final String url) {
		enduraSoapService.setServerURL(url, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				System.out.println(url + " has Been succesfully set.");
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	public void loadUsers() {
		enduraSoapService.userGetAll(new AsyncCallback<ArrayList<User>>() {
			
			@Override
			public void onSuccess(ArrayList<User> result) {
				for (User user : result) {
					if(!checkDup(user)) {
						userList.add(user);
					}
				}
				userCellTable.setRowCount(userList.size());
				userCellTable.setRowData(0,userList);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}
}
