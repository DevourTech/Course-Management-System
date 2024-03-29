package org.cms.client.framework.rest;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cms.client.framework.globals.Constants;
import org.cms.core.course.Course;
import org.cms.core.files.assignment.Assignment;
import org.cms.core.http.IdResponse;
import org.cms.core.instructor.Instructor;
import org.cms.core.student.Student;

/***
 * @
 */
public class RestClientImpl implements RestClient {

	private final HttpClient httpClient;
	private final String hostName;
	private final String userName;
	private final String password;
	private final String userType;

	public static final Logger logger = LogManager.getLogger(RestClientImpl.class);

	public RestClientImpl(String hostName, String userName, String password, String userType) {
		httpClient =
			HttpClient
				.newBuilder()
				.authenticator(
					new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(userName, password.toCharArray());
						}
					}
				)
				.build();
		this.hostName = hostName;
		this.userName = userName;
		this.password = password;
		this.userType = userType;
	}

	//for get courses
	@Override
	public CompletableFuture<List<Course>> getAllCourses() throws URISyntaxException {
		String path = "/api/courses";
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(hostName + path)).GET().build();

		return httpClient
			.sendAsync(request, HttpResponse.BodyHandlers.ofString())
			.thenApply(HttpResponse::body)
			.thenApply(s -> JsonIterator.deserialize(s, Course[].class))
			.thenApply(Arrays::asList);
	}

	@Override
	public boolean authenticate() throws Exception {
		String path = "/api/" + userType.toLowerCase() + "s/" + userName;
		logger.info(path);
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(hostName + path)).GET().build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response.statusCode() == 200;
	}

	@Override
	public CompletableFuture<List<Course>> getCoursesForUser(String userId) throws URISyntaxException {
		if (userType.equalsIgnoreCase(Constants.ADMIN)) {
			return null;
		}

		String path = "/api/" + userType.toLowerCase() + "s/" + userId + "/courses";
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(hostName + path)).GET().build();

		return httpClient
			.sendAsync(request, HttpResponse.BodyHandlers.ofString())
			.thenApply(HttpResponse::body)
			.thenApply(s -> JsonIterator.deserialize(s, Course[].class))
			.thenApply(Arrays::asList);
	}

	private CompletableFuture<IdResponse> createResource(String path, String requestBody) throws URISyntaxException {
		HttpRequest request = HttpRequest
			.newBuilder()
			.uri(new URI(hostName + path))
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(requestBody))
			.build();

		return httpClient
			.sendAsync(request, HttpResponse.BodyHandlers.ofString())
			.thenApply(HttpResponse::body)
			.thenApply(s -> JsonIterator.deserialize(s, IdResponse.class));
	}

	@Override
	public CompletableFuture<IdResponse> createStudent(Student student) throws URISyntaxException {
		String path = "/api/students";
		String requestBody = JsonStream.serialize(student);
		return createResource(path, requestBody);
	}

	@Override
	public CompletableFuture<IdResponse> createInstructor(Instructor instructor) throws URISyntaxException {
		String path = "/api/instructors";
		String requestBody = JsonStream.serialize(instructor);
		return createResource(path, requestBody);
	}

	@Override
	public CompletableFuture<IdResponse> createCourse(Course course) throws URISyntaxException {
		String path = "/api/courses";
		String requestBody = JsonStream.serialize(course);
		return createResource(path, requestBody);
	}

	@Override
	public CompletableFuture<String> subscribe(String userId, String courseId) throws URISyntaxException {
		String path = "/api/" + userType.toLowerCase() + "s/" + userId + "/courses/" + courseId;
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(hostName + path)).PUT(HttpRequest.BodyPublishers.noBody()).build();

		return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
	}

	@Override
	public CompletableFuture<String> unsubscribe(String userId, String courseId) throws URISyntaxException {
		String path = "/api/" + userType.toLowerCase() + "s/" + userId + "/courses/" + courseId;
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(hostName + path)).DELETE().build();

		return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
	}

	@Override
	public CompletableFuture<List<Assignment>> getAssignmentsWithCourses(List<String> courseIds) throws URISyntaxException {
		StringBuilder builder = new StringBuilder();
		String path = "/api/assignments/withCourses";

		builder.append(hostName).append(path).append("?");
		builder.append("c").append(1).append("=").append(courseIds.get(0));
		for (int i = 1; i < courseIds.size(); i++) {
			builder.append("&").append("c").append(i + 1).append("=").append(courseIds.get(i));
		}

		HttpRequest request = HttpRequest.newBuilder().uri(new URI(builder.toString())).GET().build();

		return httpClient
			.sendAsync(request, HttpResponse.BodyHandlers.ofString())
			.thenApply(HttpResponse::body)
			.thenApply(s -> JsonIterator.deserialize(s, Assignment[].class))
			.thenApply(Arrays::asList);
	}
}
