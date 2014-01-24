package pelco.vxst.continuousdeployment.client;

import java.util.ArrayList;

import pelco.vxst.continuousdeployment.shared.Role;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.NavForm;
import com.github.gwtbootstrap.client.ui.Row;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class RoleUI extends Composite {

	private static RoleUiBinder uiBinder = GWT.create(RoleUiBinder.class);
	
	interface RoleUiBinder extends UiBinder<Widget, RoleUI> {
	}
	
	@UiField
	NavForm priority;
	@UiField
	NavForm roleName;
	@UiField
	Button addRoleButton;
	@UiField
	FlexTable roleTable;
	@UiField
	Row dupError;
	
	private ArrayList<String> roleList = new ArrayList<String>();
	private final EnduraSoapServiceAsync enduraSoapService = GWT
			.create(EnduraSoapService.class);
	
	public RoleUI() {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		//Role Table Headers
		roleTable.setText(0, 0, "Priority");
		roleTable.setText(0, 1, "Role");
		roleTable.setText(0, 2, "Remove");
		roleTable.setCellPadding(2);
		roleTable.setBorderWidth(1);
		
		addRoleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if( !checkDup(roleName.getTextBox().getText()) )
					addRowToTable(Integer.parseInt(priority.getTextBox().getText()), roleName.getTextBox().getText());
				else
				{
					dupError.add(new Alert("Entered Duplicate Role", AlertType.ERROR, true));
				}
			}
		});
	}
	
	private Boolean checkDup (final String role) {
		if(roleList.contains(role))
			return true;
		return false;
	}
	
	private void addRowToTable(final Integer priority, final String role) {
		final int currentRow = roleTable.getRowCount();
		Button removeRoleButton = new Button("x");
		
		roleTable.setText(currentRow, 0, Integer.toString(priority));
		roleTable.setText(currentRow, 1, role);
		roleTable.setWidget(currentRow, 2, removeRoleButton);
		roleList.add(role);
		
		removeRoleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int removeIndex = roleList.indexOf(role);
				
				roleTable.removeRow(removeIndex + 1);
				roleList.remove(removeIndex);
			}
		});
	}
	
	public void loadRoles () {
		enduraSoapService.roleGetAll(new AsyncCallback<ArrayList<Role>>() {
			
			@Override
			public void onSuccess(ArrayList<Role> result) {
				for (Role role : result) {
					if(!roleList.contains(role.getName())) {
						addRowToTable(role.getPriority(), role.getName());
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}