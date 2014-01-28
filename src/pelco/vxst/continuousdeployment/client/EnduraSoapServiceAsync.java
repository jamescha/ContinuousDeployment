package pelco.vxst.continuousdeployment.client;

import java.util.ArrayList;

import pelco.vxst.continuousdeployment.shared.Role;
import pelco.vxst.continuousdeployment.shared.User;
import pelco.vxst.continuousdeployment.shared.UserAttr;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>EnduraSoapService</code>.
 */
public interface EnduraSoapServiceAsync {
	void roleGetAll(AsyncCallback<ArrayList<Role>> callback);
	void userGetAll(AsyncCallback<ArrayList<User>> callback);
	void userAttrGetAll(String user, AsyncCallback<ArrayList<UserAttr>> callback);
	void userGetRoles(Integer dbId, AsyncCallback<ArrayList<Role>> asyncCallback);
	void userCreate(String name, String pass, String userId, String roleDbId,
			AsyncCallback<String> callback);
	void userDelete(String dbId, String userId, AsyncCallback<String> callback);
	void setServerURL(String serverUrl, AsyncCallback<Void> callback);
	void getServerURL(AsyncCallback<String> callback);
}
