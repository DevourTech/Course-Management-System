package org.cms.client.framework.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.cms.core.course.Course;
import org.cms.core.files.assignment.Assignment;
import org.cms.core.http.IdResponse;
import org.cms.core.instructor.Instructor;
import org.cms.core.student.Student;

public interface RestClient {
	CompletableFuture<List<Course>> getAllCourses() throws URISyntaxException;
	boolean authenticate() throws Exception;
	CompletableFuture<List<Course>> getCoursesForUser(String userId) throws URISyntaxException;
	CompletableFuture<IdResponse> createStudent(Student student) throws URISyntaxException;
	CompletableFuture<IdResponse> createInstructor(Instructor instructor) throws URISyntaxException;
	CompletableFuture<IdResponse> createCourse(Course course) throws URISyntaxException;
	CompletableFuture<String> subscribe(String userId, String courseId) throws URISyntaxException;
	CompletableFuture<String> unsubscribe(String userId, String courseId) throws URISyntaxException;
	CompletableFuture<List<Assignment>> getAssignmentsWithCourses(List<String> courseIds) throws URISyntaxException;
}
