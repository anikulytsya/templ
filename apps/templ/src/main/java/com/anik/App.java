package com.anik;

import com.anik.binder.AppBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class App extends ResourceConfig {
	public App() {
		register(new AppBinder());
	}
}
