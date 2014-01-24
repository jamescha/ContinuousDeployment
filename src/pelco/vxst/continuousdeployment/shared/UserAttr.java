package pelco.vxst.continuousdeployment.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserAttr implements IsSerializable{
	
	Integer dbId;
	String name;
	Integer priority;
	
	public UserAttr() {
	}
	
	public UserAttr(Integer dbId, String name, Integer priority) {
		setDbId(dbId);
		setName(name);
		setPriority(priority);
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	
}
