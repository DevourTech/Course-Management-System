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
import org.cms.core.course.Course;
import org.cms.core.http.IdResponse;
import org.cms.core.instructor.Instructor;
import org.cms.core.student.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * @
 */
public class RestClientImpl implements RestClient {

	private final HttpClient httpClient;
	private final String hostName;
	private final String userName;
	private final String passWord;
	private final String userType;

	public static final Logger logger = LoggerFactory.getLogger(RestClientImpl.class);

	public RestClientImpl(String hostName, String userName, String passWord, String userType) {
		httpClient =
			HttpClient
				.newBuilder()
				.authenticator(
					new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(userName, passWord.toCharArray());
						}
					}
				)
				.build();
		this.hostName = hostName;
		this.userName = userName;
		this.passWord = passWord;
		this.userType = userType;
	}

	//for get courses
	@Override
	public CompletableFuture<List<Course>> getAllCourses() throws Exception {
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
	public CompletableFuture<List<Course>> getCoursesForStudent(String studentId) throws URISyntaxException {
		String path = "/api/students/" + studentId + "/courses";
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
}
