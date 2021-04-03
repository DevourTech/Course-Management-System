package org.cms.client.framework.rest;

import org.cms.core.course.Course;

import java.util.List;

public interface RestClient {
	List<Course> getAllCourses();
	//void subscribe(User user, Course course);
}
