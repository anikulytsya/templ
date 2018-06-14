package com.anik.binder;

import com.anik.service.Service;
import com.anik.service.Service2;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AppBinder extends AbstractBinder {
	@Override
	protected void configure() {
		bind(Service.class).to(Service.class).in(Singleton.class);
		bind(Service2.class).to(Service2.class).in(Singleton.class);
	}
}
