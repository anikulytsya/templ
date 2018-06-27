package com.anik;

import com.anik.inject.AppBinder;
import com.anik.inject.ConfigInjectionResolver;
import java.util.Properties;
import org.glassfish.jersey.server.ResourceConfig;

public class App extends ResourceConfig {
	private static final String PROJECT_NAME = "templ";
	private static final String SETTINGS_FILE_NAME = "settings";
	private static final String SYSTEM_BASE_PATH_NAME = "catalina.base";

	public App() {
		loadProperties();
		register(new AppBinder());
		register(new ConfigInjectionResolver());
	}

	private void loadProperties() {
		final SettingsLoader settingsLoader = new SettingsLoader();
		final Properties properties = settingsLoader.load(confPath());

		properties.forEach((name, value) -> property((String) name, value));
		property("xxx", "aaa");
	}

	private String confPath() {
		return String.format("%s/conf/%s/%s.properties",
				System.getProperty(SYSTEM_BASE_PATH_NAME),
				PROJECT_NAME,
				SETTINGS_FILE_NAME);
	}
}
