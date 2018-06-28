package com.anik;

import com.anik.db.PMFFactory;
import java.util.Properties;

public class DefaultSettings {
	public Properties get() {
		final Properties props = new Properties();
		//todo from resource properties

		props.put(PMFFactory.DB_DRIVER_NAME_PROP, "org.postgresql.Driver");
		props.put(PMFFactory.DB_PASSWORD_PROP, "empty");
		props.put(PMFFactory.DB_USERNAME_PROP, "aermez");
		props.put(PMFFactory.DB_URL_PROP, "jdbc:postgresql://127.0.0.1:5432/aermez");
		return props;
	};
}
