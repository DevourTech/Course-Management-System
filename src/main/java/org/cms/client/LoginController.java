package org.cms.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.cms.client.framework.globals.Globals;
import org.cms.client.framework.session.Session;

public class LoginController implements Initializable {

	public static final String ROOT_LAYOUT = "/fxml/root.fxml";
	public TextField usernameField;
	public PasswordField passwdField;
	private Parent mainView;

	Session session = Session.getInstance();

	public void loginOnAction(ActionEvent actionEvent) {
		Globals.getStage().getScene().setRoot(mainView);
		session.setId(Integer.parseInt(usernameField.getText()));
		session.setPasswd(passwdField.getText());
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
