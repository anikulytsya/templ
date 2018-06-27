package com.anik.inject;

import com.anik.annotations.Config;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;

public class ConfigInjectionResolver implements InjectionResolver<Config> {
	@Context
	private Configuration configuration;

	@Override
	public Object resolve(final Injectee injectee, final ServiceHandle<?> handle) {
		if (String.class == injectee.getRequiredType()) {
			final Config annotation = injectee.getParent().getAnnotation(Config.class);

			if (annotation != null) {
				final String prop = annotation.value();

				return (String) configuration.getProperty(prop);
			}
		}

		return null;
	}

	@Override
	public boolean isConstructorParameterIndicator() {
		return false;
	}

	@Override
	public boolean isMethodParameterIndicator() {
		return false;
	}
}
