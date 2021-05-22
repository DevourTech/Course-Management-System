package org.cms.client.controllers;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.cms.client.framework.globals.Globals;
import org.cms.client.framework.service.Service;
import org.cms.client.framework.session.Session;
import org.cms.core.course.Course;
import org.cms.core.http.IdResponse;

public class CreateCourseController {

	public TextField nameField;
	public TextField branchField;
	public TextField descriptionField;

	public void createCourseByAdminOnAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException, URISyntaxException {
		String name = nameField.getText();
		String branch = branchField.getText();
		String description = descriptionField.getText();

		Node source = (Node) actionEvent.getSource();
		Stage stage = (Stage) source.getScene().getWindow();

		CompletableFuture<IdResponse> createCourseResponse;

		Course course = new Course();
		course.setName(name);
		course.setBranch(branch);
		course.setDescription(description);
		createCourseResponse = Service.getInstance().getSession().getRestClient().createCourse(course);

		Globals.idResponse = createCourseResponse.get();
		stage.close();
	}
}
