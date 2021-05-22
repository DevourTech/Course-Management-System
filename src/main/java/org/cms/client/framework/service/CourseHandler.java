package org.cms.client.framework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import javafx.collections.ObservableList;
import org.cms.core.course.Course;

public class CourseHandler {

	private ObservableList<Course> subscribedCourses;
	private final Map<String, Boolean> isSubscribed = new HashMap<>();
	private final Semaphore mutex;

	public CourseHandler() {
		mutex = new Semaphore(1);
	}

	public List<Course> getSubscribedCourses() {
		try {
			lock();
			return subscribedCourses;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			unlock();
		}
		return new ArrayList<>();
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

	public void removeCourseFromSubscribedList(Course c) {
		subscribedCourses.removeIf(course -> course.getId().equals(c.getId()));
		isSubscribed.put(c.getId(), false);
	}

	public void lock() throws InterruptedException {
		mutex.acquire();
	}

	public void unlock() {
		mutex.release();
	}
}
