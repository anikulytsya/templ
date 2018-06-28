package com.anik.db;

import com.anik.annotations.Config;
import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.CloseableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PMFFactory implements Factory<PMF> {
	private static final Logger LOG = LoggerFactory.getLogger(PMFFactory.class);

	private final PMF pmf;
	private final CloseableService closeableService;

	@Inject
	public PMFFactory(
			final CloseableService closeableService,
			@Config(PMF.DB_DRIVER_NAME_PROP)
			final String dbDriverName,
			@Config(PMF.DB_URL_PROP)
			final String dbUrl,
			@Config(PMF.DB_USERNAME_PROP)
			final String dbUsername,
			@Config(PMF.DB_PASSWORD_PROP)
			final String dbPassword) {
		this.closeableService = closeableService;
		this.pmf = new PMF(closeableService, dbDriverName, dbUrl, dbUsername, dbPassword);
	}

	@Override
	public void dispose(final PMF pmf) {
	}

	@Override
	public PMF provide() {
		return pmf;
	}
}
