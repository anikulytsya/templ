package com.anik;

import com.anik.db.PMF;
import java.util.Properties;

public class DefaultSettings {
	public Properties get() {
		final Properties props = new Properties();
		//todo from resource properties

		props.put(PMF.DB_DRIVER_NAME_PROP, "org.postgresql.Driver");
		props.put(PMF.DB_PASSWORD_PROP, "empty");
		props.put(PMF.DB_USERNAME_PROP, "aermez");
		props.put(PMF.DB_URL_PROP, "jdbc:postgresql://127.0.0.1:5432/aermez");

		return props;
	};
}
