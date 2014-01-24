package pelco.vxst.continuousdeployment.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {
	Integer dbId;
	String name;
	Integer maxLogins;
	UserAttr userAttr=new UserAttr();
	
	public User() {}

	public User(Integer dbId, String name, Integer maxLogins) {
		setDbId(dbId);
		setName(name);
		setMaxLogins(maxLogins);
	}
	
	public Integer getDbId() {
		return dbId;
	}
	public void setDbId(Integer dbId) {
		this.dbId = dbId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getMaxLogins() {
		return maxLogins;
	}
	public void setMaxLogins(Integer maxLogins) {
		this.maxLogins = maxLogins;
	}

	public UserAttr getUserAttr() {
		return userAttr;
	}
	public void setUserAttr(UserAttr userAttr) {
		this.userAttr = userAttr;
	}
	
}
