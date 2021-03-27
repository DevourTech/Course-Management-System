package org.cms.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.cms.client.framework.config.Config;
import org.cms.client.framework.globals.Globals;

public class Main extends Application {

	public static final String CONFIG_FILE = "config/client.properties";
	public static final String LOGIN_LAYOUT = "/fxml/login.fxml";

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(LOGIN_LAYOUT));
		Config.load(getClass().getClassLoader(), CONFIG_FILE);

		int stageWidth = Integer.parseInt(Config.get(Config.STAGE_WIDTH));
		int stageHeight = Integer.parseInt(Config.get(Config.STAGE_HEIGHT));

		Scene scene = new Scene(root);

		primaryStage.setTitle(Config.get(Config.STAGE_TITLE));
		primaryStage.setScene(scene);
		primaryStage.show();
		Globals.setStage(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
