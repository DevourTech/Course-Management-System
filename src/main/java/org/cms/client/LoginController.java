package org.cms.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import org.cms.client.framework.globals.Globals;

public class LoginController implements Initializable {

	public static final String ROOT_LAYOUT = "/fxml/root.fxml";
	private Parent mainView;

	public void loginOnAction(ActionEvent actionEvent) {
		Globals.getStage().getScene().setRoot(mainView);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			mainView = FXMLLoader.load(getClass().getResource(ROOT_LAYOUT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
