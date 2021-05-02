package org.cms.client.controllers;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.cms.client.framework.globals.Constants;
import org.cms.client.framework.globals.Globals;
import org.cms.client.framework.session.Session;
import org.cms.client.ui.UserDialog;
import org.cms.core.http.IdResponse;
import org.cms.core.instructor.Instructor;
import org.cms.core.student.Student;

public class CreateUserController {

	public Button createStudent;
	public TextField nameField;
	public TextField passwordField;

	public void createUserByAdminOnAction(ActionEvent actionEvent) throws URISyntaxException, ExecutionException, InterruptedException {
		String name = nameField.getText();
		String password = passwordField.getText();

		Node source = (Node) actionEvent.getSource();
		Stage userStage = (Stage) source.getScene().getWindow();
		UserDialog userDialog = (UserDialog) userStage;

		String sourceNodeId = userDialog.getSourceNode().getId();
		System.out.println(sourceNodeId);

		Session session = Session.getInstance();
		CompletableFuture<IdResponse> createUserResponse;

		if (sourceNodeId.equalsIgnoreCase(Constants.CREATE_STUDENT_BY_ADMIN)) {
			Student student = new Student();
			student.setName(name);
			student.setPassword(password);
			createUserResponse = session.getRestClient().createStudent(student);
		} else {
			Instructor instructor = new Instructor();
			instructor.setName(name);
			instructor.setPassword(password);
			createUserResponse = session.getRestClient().createInstructor(instructor);
		}
		Globals.idResponse = createUserResponse.get();
		userDialog.close();
	}
}
