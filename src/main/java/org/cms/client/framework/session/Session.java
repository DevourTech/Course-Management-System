package org.cms.client.framework.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import org.cms.client.framework.rest.RestClient;
import org.cms.client.framework.rest.RestClientImpl;
import org.cms.core.course.Course;

public class Session {

	private String userId;
	private String userPassword;
	private String userType;
	private ObservableList<Course> subscribedCourses;
	private final Map<String, Boolean> isSubscribed = new HashMap<>();

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

	public List<Course> getSubscribedCourses() {
		return subscribedCourses;
	}

	public void setSubscribedCourses(ObservableList<Course> subscribedCourses) {
		this.subscribedCourses = subscribedCourses;
		for (Course c : subscribedCourses) {
			isSubscribed.put(c.getId(), true);
		}
	}

	public boolean isCourseSubscribed(Course c) {
		if (isSubscribed.containsKey(c.getId())) {
			return isSubscribed.get(c.getId());
		}
		return false;
	}

	public void addCourseToSubscribeList(Course c) {
		subscribedCourses.add(c);
		isSubscribed.put(c.getId(), true);
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
