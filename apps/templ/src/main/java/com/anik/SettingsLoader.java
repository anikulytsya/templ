package com.anik;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsLoader {
	private static final Logger LOG = LoggerFactory.getLogger(SettingsLoader.class);

	public Properties load(final String path) {
		final DefaultSettings defaultSettings = new DefaultSettings();
		final Properties defaults = defaultSettings.get();
		final Properties properties = new Properties(defaults);

		LOG.info("Loading settings from: {}", path);

		try (FileInputStream is = new FileInputStream(path)) {
			properties.load(is);
		} catch (final FileNotFoundException ex) {
			LOG.warn("Settings file not found: {}", path);
		} catch (final IOException ex) {
			LOG.warn("Cannot open settings file: {}", path);
		}

		return properties;
	}
}
