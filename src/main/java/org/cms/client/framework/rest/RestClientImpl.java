package org.cms.client.framework.rest;

import com.jsoniter.JsonIterator;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.cms.core.course.Course;

/***
 * @
 */
public class RestClientImpl implements RestClient {

	private final HttpClient httpClient;
	private final String hostName;
	private final String userName;
	private final String passWord;
	private final String userType;

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
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(hostName + path)).GET().build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response.statusCode() == 200;
	}
}
