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
	private Parent coursesView;
	private Parent homeView;

	public static final String COURSES_TABLE_VIEW = "/fxml/course-table.fxml";
	public static final String HOME_VIEW = "/fxml/home.fxml";

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
		coursesView = FXMLLoader.load(getClass().getResource(COURSES_TABLE_VIEW));
		homeView = FXMLLoader.load(getClass().getResource(HOME_VIEW));
	}

	public void coursesButtonAction(ActionEvent actionEvent) {
		primaryStageBody.getChildren().setAll(coursesView);
	}

	public void homeButtonAction(ActionEvent actionEvent) {
		primaryStageBody.getChildren().setAll(homeView);
	}
}
