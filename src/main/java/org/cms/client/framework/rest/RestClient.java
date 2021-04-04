package org.cms.client.framework.rest;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.cms.core.course.Course;

public interface RestClient {
	CompletableFuture<List<Course>> getAllCourses() throws Exception;
	boolean authenticate() throws Exception;
	//void subscribe(User user, Course course);
}
