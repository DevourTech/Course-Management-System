package org.cms.client.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class RootController implements Initializable {

	public VBox userPopup;
	public AnchorPane primaryStageBody;
	private Parent coursesView, homeView, adminView, assignmentView;

	private FXMLLoader homeViewLoader, assignmentViewLoader;

	public static final String COURSES_TABLE_VIEW = "/fxml/course-table.fxml";
	public static final String HOME_VIEW = "/fxml/home.fxml";
	public static final String ADMIN_VIEW = "/fxml/admin.fxml";
	public static final String ASSIGNMENT_VIEW = "/fxml/assignments.fxml";

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			initViews();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		primaryStageBody.getChildren().setAll(homeView);
	}

	private void initViews() throws IOException {
		homeViewLoader = new FXMLLoader(getClass().getResource(HOME_VIEW));
		homeView = homeViewLoader.load();

		adminView = FXMLLoader.load(getClass().getResource(ADMIN_VIEW));

		assignmentViewLoader = new FXMLLoader(getClass().getResource(ASSIGNMENT_VIEW));
		assignmentView = assignmentViewLoader.load();
	}

	public void coursesButtonAction(ActionEvent actionEvent) throws IOException {
		if (coursesView == null) {
			coursesView = FXMLLoader.load(getClass().getResource(COURSES_TABLE_VIEW));
		}
		primaryStageBody.getChildren().setAll(coursesView);
	}

	public void homeButtonAction(ActionEvent actionEvent) throws IOException {
		primaryStageBody.getChildren().setAll(homeView);
	}

	public void manageButtonAction(ActionEvent actionEvent) throws IOException {
		primaryStageBody.getChildren().setAll(adminView);
	}

	public void assignmentOnAction(ActionEvent actionEvent) {
		primaryStageBody.getChildren().setAll(assignmentView);
	}

	public HomeController getHomeController() {
		return homeViewLoader.getController();
	}

	public AssignmentController getAssignmentController() {
		return assignmentViewLoader.getController();
	}
}
