package com.anik.db;

import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.CloseableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PMF implements Factory<PersistenceManager> {
	private static final Logger LOG = LoggerFactory.getLogger(PMF.class);

	private final PersistenceManagerFactory pmf;
	private final CloseableService closeableService;

	@Inject public PMF(
			final CloseableService closeableService,
			final PersistenceManagerFactory pmf) {
		LOG.info("create PMF");
		this.closeableService = closeableService;
		this.pmf = pmf;
	}

	@Override
	public void dispose(final PersistenceManager pm) {
		if (pm != null && !pm.isClosed()) {
			try {
				if (pm.currentTransaction().isActive()) {
					LOG.info("rollback open transaction");
					pm.currentTransaction().rollback();
				}
			} catch (final Exception ex) {
				LOG.warn("Error during transaction rollback", ex);
			}

			try {
				LOG.info("close PM");
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
		closeableService.add(() -> dispose(pm));
		LOG.info("create PM");
		return pm;
	}
}
