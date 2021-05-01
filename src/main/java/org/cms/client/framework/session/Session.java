package org.cms.client.framework.session;

import org.cms.client.framework.rest.RestClient;
import org.cms.client.framework.rest.RestClientImpl;

public class Session {

	private String userId;
	private String userPassword;
	private String userType;

	private static final Session session = new Session();
	private RestClient restClient;

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

	public RestClient getRestClient() {
		return restClient;
	}

	public void initialize(String hostName, String userId, String password, String userType) {
		session.setUserId(userId);
		session.setUserPassword(password);
		session.setUserType(userType);
		restClient = new RestClientImpl(hostName, userId, password, userType);
	}

	@Override
	public String toString() {
		return "Session{" + "userId='" + userId + '\'' + ", userType='" + userType + '\'' + '}';
	}
}
