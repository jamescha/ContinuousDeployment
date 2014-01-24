package pelco.vxst.continuousdeployment.client;
import pelco.vxst.continuousdeployment.client.RoleUI;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
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
	
	@UiHandler("addRole")
	void handleAddRole(ClickEvent event) {
		RootPanel.get("content").clear();
		roles.loadRoles(); //Load Roles from the SM
		RootPanel.get("content").add(roles);
	}
	
	@UiHandler("addUser")
	void handleAddUser(ClickEvent event) {
		RootPanel.get("content").clear();
		users.loadUsers(); //Load Users from the SM
		RootPanel.get("content").add(users);
	}
}
