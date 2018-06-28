package com.anik.db;

import com.anik.annotations.Config;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public final class PMF {
	private static final Logger LOG = LoggerFactory.getLogger(PMF.class);
	public static final String DB_DRIVER_NAME_PROP = "db.driver.name";
	public static final String DB_URL_PROP = "db.url";
	public static final String DB_USERNAME_PROP = "db.username";
	public static final String DB_PASSWORD_PROP = "db.password";

	private final PersistenceManagerFactory factory;

	@Inject public PMF(
			@Config(DB_DRIVER_NAME_PROP) final String dbDriverName,
			@Config(DB_URL_PROP) final String dbUrl,
			@Config(DB_USERNAME_PROP) final String dbUsername,
			@Config(DB_PASSWORD_PROP) final String dbPassword) {
		final Map<String, String> args = new HashMap<>();

		args.put("javax.jdo.option.ConnectionDriverName", dbDriverName);
		args.put("javax.jdo.option.ConnectionURL", dbUrl);
		args.put("javax.jdo.option.ConnectionUserName", dbUsername);
		args.put("javax.jdo.option.ConnectionPassword", dbPassword);
		factory = JDOHelper.getPersistenceManagerFactory(args, "db00");
	}

	public PersistenceManagerFactory get() {
		return factory;
	}
}
