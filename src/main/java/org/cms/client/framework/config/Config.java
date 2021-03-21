package org.cms.client.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

	public static final String STAGE_WIDTH = "stage.width";
	public static final String STAGE_HEIGHT = "stage.height";
	public static final String CMS_HOST = "cms.host";
	public static final String CMS_API_PATH = "cms.api.path";
	public static final String STAGE_TITLE = "stage.title";

	public static final Logger logger = LoggerFactory.getLogger(Config.class.getName());
	private static Properties configuration;

	public static void load(ClassLoader loader, String configFile) {
		configuration = new Properties();
		InputStream inputStream = loader.getResourceAsStream(configFile);
		try {
			configuration.load(inputStream);
		} catch (IOException e) {
			logger.error("Error in loading client configuration", e);
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return configuration.getProperty(key);
	}
}
