package pelco.vxst.continuousdeployment.client;
import com.github.gwtbootstrap.client.ui.Form.SubmitCompleteEvent;
import com.github.gwtbootstrap.client.ui.Form.SubmitCompleteHandler;
import com.github.gwtbootstrap.client.ui.Form.SubmitEvent;
import com.github.gwtbootstrap.client.ui.Form.SubmitHandler;
import com.github.gwtbootstrap.client.ui.NavForm;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class HeaderUI extends Composite {

	private static HeaderUIUiBinder uiBinder = GWT
			.create(HeaderUIUiBinder.class);

	interface HeaderUIUiBinder extends UiBinder<Widget, HeaderUI> {
	}

	public HeaderUI() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	final RoleUI roles = new RoleUI();
	final UserUI users = new UserUI(); 
	
	@UiField
	NavForm ipAddress;
	
/*	@UiHandler("addRole")
	void handleAddRole(ClickEvent event) {
		RootPanel.get("content").clear();
		roles.loadRoles(); //Load Roles from the SM
		RootPanel.get("content").add(roles);
	}*/
	
	@UiHandler("addUser")
	void handleAddUser(ClickEvent event) {
		
		if(ipAddress.getTextBox().getText().length() == 0) {
			Window.alert("You Need To Set the IP Address");
		} else {
			RootPanel.get("content").clear();
			users.loadUsers(); //Load Users from the SM
			RootPanel.get("content").add(users);
		}
	}
	
	
	@UiHandler("ipAddress")
	void handleText(SubmitEvent event){
		
		if(ipAddress.getTextBox().getText().length() == 0) {			
			Window.alert("Please Add a Ip Address");
			event.cancel();
		} else {
			Window.alert(ipAddress.getTextBox().getText());
			users.setUrl(ipAddress.getTextBox().getText());
		}
			
	}
}
