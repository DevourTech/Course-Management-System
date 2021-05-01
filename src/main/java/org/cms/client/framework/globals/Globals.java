package org.cms.client.framework.globals;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;
import org.cms.core.http.IdResponse;

public class Globals {

	public static Stage appStage = null;

	public static Stage getStage() {
		return appStage;
	}

	public static void setStage(Stage stage) {
		appStage = stage;
	}

	public static IdResponse idResponse;
}
