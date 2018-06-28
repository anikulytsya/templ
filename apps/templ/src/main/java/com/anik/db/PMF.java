package com.anik.db;

import com.anik.annotations.Config;
import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.CloseableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PMF implements Factory<PersistenceManager> {
	private static final Logger LOG = LoggerFactory.getLogger(PMF.class);
	public static final String DB_DRIVER_NAME_PROP = "db.driver.name";
	public static final String DB_URL_PROP = "db.url";
	public static final String DB_USERNAME_PROP = "db.username";
	public static final String DB_PASSWORD_PROP = "db.password";

	private final PersistenceManagerFactory pmf;
	private final CloseableService closeableService;

	public PMF(
			final CloseableService closeableService,
			final String dbDriverName,
			final String dbUrl,
			final String dbUsername,
			final String dbPassword) {
		final Map<String, String> args = new HashMap<>();

		LOG.warn("driver: {}, url: {}, user: {}, password: {}", dbDriverName, dbUrl, dbUsername, dbPassword);

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
		this.closeableService = closeableService;
		this.pmf = JDOHelper.getPersistenceManagerFactory(args);
	}

	@Override
	public void dispose(final PersistenceManager pm) {
		if (pm != null && !pm.isClosed()) {
			try {
				if (pm.currentTransaction().isActive()) {
					LOG.warn("rollback open transaction");
					pm.currentTransaction().rollback();
				}
			} catch (final Exception ex) {
				LOG.warn("Error during transaction rollback", ex);
			}

			try {
				LOG.warn("close PM");
				pm.close();
			} catch (final Exception ex) {
				LOG.warn("Error during closing persistence manager", ex);
			}
		}
	}

	@Override
	public PersistenceManager provide() {
		final PersistenceManager pm = pmf.getPersistenceManager();

		pm.currentTransaction().begin();

		closeableService.add(new Closeable() {
			public final void close() {
				dispose(pm);
			}
		});

		LOG.warn("create PM");
		return pm;
	}
}
