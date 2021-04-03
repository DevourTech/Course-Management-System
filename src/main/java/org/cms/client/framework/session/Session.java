package org.cms.client.framework.session;

public class Session {

	String userId;
	String userPassword;
	String userType;

	public static final Session session = new Session();

	private Session() {}

	//singleton
	public static Session getInstance() {
		return session;
	}

	public void setUserId(String id) {
		this.userId = id;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String passwd) {
		this.userPassword = passwd;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "Session{" +
				"userId='" + userId + '\'' +
				", userType='" + userType + '\'' +
				'}';
	}
}
