package org.cms.client.controllers;

import com.jfoenix.controls.JFXRadioButton;
import java.io.IOException;
import java.net.URISyntaxException;
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
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cms.client.framework.config.Config;
import org.cms.client.framework.globals.Globals;
import org.cms.client.framework.service.Service;
import org.cms.client.framework.service.ServiceConfig;
import org.cms.client.framework.session.FailedAuthenticationException;
import org.cms.client.framework.session.SessionConfig;

public class LoginController implements Initializable {

	public static final String ROOT_LAYOUT = "/fxml/root.fxml";
	public TextField usernameField;
	public PasswordField passwordField;
	public JFXRadioButton studentRadio;
	public JFXRadioButton instructorRadio;
	public JFXRadioButton adminRadio;

	private FXMLLoader mainViewLoader;
	private Parent mainView;

	private final ToggleGroup toggleGroup = new ToggleGroup();

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			mainViewLoader = new FXMLLoader(getClass().getResource(ROOT_LAYOUT));
			mainView = mainViewLoader.load();
			studentRadio.setToggleGroup(toggleGroup);
			instructorRadio.setToggleGroup(toggleGroup);
			adminRadio.setToggleGroup(toggleGroup);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loginOnAction(ActionEvent actionEvent) {
		logger.info("Attempt to login to CMS Client Application");
		ServiceConfig serviceConfig = createServiceConfig();
		try {
			Service.init(serviceConfig);
			postInitUIUpdate();
		} catch (FailedAuthenticationException e) {
			// TODO : Update UI with red markers indicating error
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ServiceConfig createServiceConfig() {
		ServiceConfig serviceConfig = new ServiceConfig();

		RadioButton selectedRadio = (RadioButton) toggleGroup.getSelectedToggle();
		SessionConfig sessionConfig = new SessionConfig(
			selectedRadio.getText().toLowerCase(),
			usernameField.getText(),
			passwordField.getText(),
			Config.get(Config.CMS_HOST)
		);
		serviceConfig.setSessionConfig(sessionConfig);

		RootController rootController = mainViewLoader.getController();
		serviceConfig.setAssignmentList(rootController.getAssignmentController().getAssignmentList());
		return serviceConfig;
	}

	private void postInitUIUpdate() {
		Stage mainStage = Globals.getStage();
		mainStage.getScene().setRoot(mainView);
		mainStage.setHeight(800);
		mainStage.setWidth(1200);

		RootController rootController = mainViewLoader.getController();
		rootController.getHomeController().populateCoursesTable();
		rootController.getAssignmentController().populateAssignmentTable();
	}
}
