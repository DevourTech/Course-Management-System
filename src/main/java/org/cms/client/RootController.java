package org.cms.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

public class RootController implements Initializable {

	public AnchorPane iconPane;
	public VBox userPopup;
	public AnchorPane primaryStageBody;
	private PopOver popOver;

	public static final String COURSES_TABLE_VIEW = "/fxml/course-table.fxml";

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		popOver = new PopOver(userPopup);
	}

	public void showUserPopup(MouseEvent mouseEvent) {
		popOver.show(iconPane, 10.0D);
	}

	public void hideUserPopup(MouseEvent mouseEvent) {
		popOver.hide();
	}

	public void coursesButtonAction(ActionEvent actionEvent) throws IOException {
		primaryStageBody.getChildren().removeAll();
		primaryStageBody.getChildren().add(FXMLLoader.load(getClass().getResource(COURSES_TABLE_VIEW)));
	}
}
