package org.cms.client.framework.session;

public class SessionConfig {

	private String userType;
	private String userId;
	private String password;
	private String hostname;

	public SessionConfig(String userType, String userId, String password, String hostname) {
		this.userType = userType;
		this.userId = userId;
		this.password = password;
		this.hostname = hostname;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Override
	public String toString() {
		return "SessionConfig{" + "userType='" + userType + '\'' + ", userId='" + userId + '\'' + ", hostname='" + hostname + '\'' + '}';
	}
}
