package com.anik.inject;

import com.anik.annotations.Config;
import com.anik.db.PMF;
import com.anik.db.PMFFactory;
import com.anik.service.Service;
import com.anik.service.Service2;
import javax.inject.Singleton;
import javax.jdo.PersistenceManager;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

public class AppBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(Service.class).to(Service.class).in(Singleton.class);
		bind(Service2.class).to(Service2.class).in(Singleton.class);
		bind(ConfigInjectionResolver.class)
			.to(new TypeLiteral<InjectionResolver<Config>>() {})
			.in(Singleton.class);
		bindFactory(PMFFactory.class)
			.to(PMF.class)
			.in(Singleton.class);
		bindFactory(PMF.class)
			.to(PersistenceManager.class)
			.in(RequestScoped.class);
	}
}
