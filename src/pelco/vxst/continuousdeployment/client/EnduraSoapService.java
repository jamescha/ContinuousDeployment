package pelco.vxst.continuousdeployment.client;

import java.util.ArrayList;

import pelco.vxst.continuousdeployment.shared.Role;
import pelco.vxst.continuousdeployment.shared.User;
import pelco.vxst.continuousdeployment.shared.UserAttr;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("enduraSoap")
public interface EnduraSoapService extends RemoteService {
	ArrayList<Role> roleGetAll();
	ArrayList<User> userGetAll();
	ArrayList<UserAttr> userAttrGetAll(String user);
	ArrayList<Role> userGetRoles(Integer dbId);
	String userCreate(String name, String pass, String userId);
	String userDelete(String dbId, String userId);
}