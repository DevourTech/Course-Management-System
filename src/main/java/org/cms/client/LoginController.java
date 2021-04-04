package org.cms.client;

import com.jfoenix.controls.JFXRadioButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import okhttp3.*;
import org.cms.client.framework.config.Config;
import org.cms.client.framework.globals.Globals;
import org.cms.client.framework.security.PasswordEncoder;
import org.cms.client.framework.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController implements Initializable {

	public static final String ROOT_LAYOUT = "/fxml/root.fxml";
	public TextField usernameField;
	public PasswordField passwordField;
	public JFXRadioButton studentRadio;
	public JFXRadioButton instructorRadio;
	public JFXRadioButton adminRadio;
	private Parent mainView;

	private static final Session session = Session.getInstance();
	private final ToggleGroup toggleGroup = new ToggleGroup();

	private static final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().followRedirects(false);
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			mainView = FXMLLoader.load(getClass().getResource(ROOT_LAYOUT));
			studentRadio.setToggleGroup(toggleGroup);
			instructorRadio.setToggleGroup(toggleGroup);
			adminRadio.setToggleGroup(toggleGroup);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loginOnAction(ActionEvent actionEvent) throws Exception {
		initializeSession();
		boolean authStatus = session.getRestClient().authenticate();
		if (!authStatus) {
			logger.error("Authentication failed");
			// TODO: Coloring of text fields......validity
			return;
		}
		logger.info("Auth success");
		Globals.getStage().getScene().setRoot(mainView);
	}

	private String extractHostNameFromConfig() {
		return Config.get(Config.CMS_HOST);
	}

	private void initializeSession() {
		String hostName = extractHostNameFromConfig();
		RadioButton selectedRadio = (RadioButton) toggleGroup.getSelectedToggle();
		String userType = selectedRadio.getText().toLowerCase();
		String password = encodePassword(passwordField.getText());
		session.initialize(hostName, usernameField.getText(), password, userType);
		logger.info("Session initialized successfully");
	}

	private String encodePassword(String password) {
		int rounds = Integer.parseInt(Config.get(Config.BCRYPT_ROUNDS));
		PasswordEncoder passwordEncoder = new PasswordEncoder(rounds);
		return passwordEncoder.hashPassword(password);
	}
}
