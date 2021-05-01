package org.cms.client;

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

	public AnchorPane iconPane;
	public VBox userPopup;
	public AnchorPane primaryStageBody;
	private Parent coursesView, homeView, adminView;

	public static final String COURSES_TABLE_VIEW = "/fxml/course-table.fxml";
	public static final String HOME_VIEW = "/fxml/home.fxml";
	public static final String ADMIN_VIEW = "/fxml/admin.fxml";

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
		homeView = FXMLLoader.load(getClass().getResource(HOME_VIEW));
		adminView = FXMLLoader.load(getClass().getResource(ADMIN_VIEW));
	}

	public void coursesButtonAction(ActionEvent actionEvent) throws IOException {
		if (coursesView == null) {
			coursesView = FXMLLoader.load(getClass().getResource(COURSES_TABLE_VIEW));
		}
		primaryStageBody.getChildren().setAll(coursesView);
	}

	public void homeButtonAction(ActionEvent actionEvent) {
		primaryStageBody.getChildren().setAll(homeView);
	}

	public void manageButtonAction(ActionEvent actionEvent) throws IOException {
		primaryStageBody.getChildren().setAll(adminView);
	}
}
