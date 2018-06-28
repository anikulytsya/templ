package com.anik.db;

import com.anik.annotations.Config;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import org.glassfish.hk2.api.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PMFFactory implements Factory<PersistenceManagerFactory> {
	private static final Logger LOG = LoggerFactory.getLogger(PMFFactory.class);
	public static final String DB_DRIVER_NAME_PROP = "db.driver.name";
	public static final String DB_URL_PROP = "db.url";
	public static final String DB_USERNAME_PROP = "db.username";
	public static final String DB_PASSWORD_PROP = "db.password";

	private final PersistenceManagerFactory pmf;

	@Inject
	public PMFFactory(
			@Config(DB_DRIVER_NAME_PROP)
			final String dbDriverName,
			@Config(DB_URL_PROP)
			final String dbUrl,
			@Config(DB_USERNAME_PROP)
			final String dbUsername,
			@Config(DB_PASSWORD_PROP)
			final String dbPassword) {
		final Map<String, String> args = new HashMap<>();

		args.put("javax.jdo.option.ConnectionDriverName", dbDriverName);
		args.put("javax.jdo.option.ConnectionURL", dbUrl);
		args.put("javax.jdo.option.ConnectionUserName", dbUsername);
		args.put("javax.jdo.option.ConnectionPassword", dbPassword);
		args.put("datanucleus.identifier.case", "LowerCase");
		args.put("datanucleus.schema.autoCreateAll", "false");
		args.put("datanucleus.schema.autoCreateTables", "false");
		args.put("datanucleus.schema.autoCreateColumns", "false");
		args.put("datanucleus.schema.autoCreateConstraints", "false");
		args.put("datanucleus.cache.level2.type", "none");
		LOG.info("Create PersistenceManagerFactory driver: {}, url: {}, user: {}, password: {}", dbDriverName, dbUrl, dbUsername, dbPassword);
		this.pmf = JDOHelper.getPersistenceManagerFactory(args);
	}

	@Override
	public PersistenceManagerFactory provide() {
		return pmf;
	}

	@Override
	public void dispose(final PersistenceManagerFactory pmf) {
	}
}
