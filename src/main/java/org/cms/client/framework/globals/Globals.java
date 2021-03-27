package org.cms.client.framework.globals;

import javafx.stage.Stage;

public class Globals {

	public static Stage appStage = null;

	public static Stage getStage() {
		return appStage;
	}

	public static void setStage(Stage stage) {
		appStage = stage;
	}
}
