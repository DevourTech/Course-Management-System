package org.cms.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.cms.client.framework.globals.Globals;
import org.cms.client.ui.UserDialog;

public class AdminController {

	public static final String CREATE_USER_LAYOUT = "/fxml/userInput.fxml";
	public static final String CREATE_COURSE_LAYOUT = "/fxml/courseInput.fxml";
	public Label statusLabel;

	private void createUserStage(UserDialog userDialog, String title) throws Exception {
		Parent createUserView = FXMLLoader.load(getClass().getResource(CREATE_USER_LAYOUT));
		userDialog.initModality(Modality.APPLICATION_MODAL);
		userDialog.setTitle(title);
		userDialog.setScene(new Scene(createUserView));
		userDialog.showAndWait();
		statusLabel.setText(Globals.idResponse.getMessage());
	}

	public void createStudentOnAction(ActionEvent actionEvent) throws Exception {
		UserDialog userDialog = new UserDialog();
		userDialog.setSourceNode((Node) actionEvent.getSource());
		createUserStage(userDialog, "Create new Student");
	}

	public void createInstructorOnAction(ActionEvent actionEvent) throws Exception {
		UserDialog userDialog = new UserDialog();
		userDialog.setSourceNode((Node) actionEvent.getSource());
		createUserStage(userDialog, "Create new Instructor");
	}

	public void createCourseOnAction(ActionEvent actionEvent) throws Exception {
		Parent createCourseView = FXMLLoader.load(getClass().getResource(CREATE_COURSE_LAYOUT));
		Stage courseStage = new Stage();
		courseStage.initModality(Modality.APPLICATION_MODAL);
		courseStage.setTitle("Create a new course");
		courseStage.setScene(new Scene(createCourseView));
		courseStage.showAndWait();
		statusLabel.setText(Globals.idResponse.getMessage());
	}
}
