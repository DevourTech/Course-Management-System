package org.cms.client;

import com.jfoenix.controls.JFXRadioButton;
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
import org.cms.client.framework.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

	public void loginOnAction(ActionEvent actionEvent) throws IOException {
		session.setUserId(usernameField.getText());
		session.setUserPassword(passwordField.getText());
		RadioButton selectedRadio = (RadioButton) toggleGroup.getSelectedToggle();
		String userType = selectedRadio.getText().toLowerCase();
		session.setUserType(userType);
		boolean authStatus = authenticateCredentials();
		if (!authStatus) {
			logger.error("Authentication failed");
			// TODO: Coloring of text fields......validity
			return;
		}
		logger.info("Auth success");
		Globals.getStage().getScene().setRoot(mainView);
	}

	private boolean authenticateCredentials() throws IOException {
		clientBuilder.authenticator((route, response) -> {
			String credential = Credentials.basic(session.getUserId(), session.getUserPassword());
			return response.request().newBuilder().header("Authorization", credential).build();
		});

		OkHttpClient client = clientBuilder.build();
		Request request = new Request.Builder()
				.url(getAuthURL())
				.build();
		Call call = client.newCall(request);
		Response response = call.execute();
		return response.code() == 200;
	}

	private String getAuthURL() {
		String BASE_URL = Config.get(Config.CMS_HOST) + Config.get(Config.CMS_API_PATH);
		String authURL = BASE_URL + "/" + session.getUserType() + "s" + "/" + session.getUserId();
		System.out.println(authURL);
		return authURL;
	}
}
