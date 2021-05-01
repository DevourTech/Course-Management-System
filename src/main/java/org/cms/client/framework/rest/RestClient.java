package org.cms.client.framework.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.cms.core.course.Course;
import org.cms.core.http.IdResponse;
import org.cms.core.instructor.Instructor;
import org.cms.core.student.Student;

public interface RestClient {
	CompletableFuture<List<Course>> getAllCourses() throws Exception;
	boolean authenticate() throws Exception;
	CompletableFuture<List<Course>> getCoursesForStudent(String studentId) throws URISyntaxException;
	CompletableFuture<IdResponse> createStudent(Student student) throws URISyntaxException;
	CompletableFuture<IdResponse> createInstructor(Instructor instructor) throws URISyntaxException;

	CompletableFuture<IdResponse> createCourse(Course course) throws URISyntaxException;
}
