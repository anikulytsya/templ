package com.anik.inject;

import com.anik.annotations.Config;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigInjectionResolver implements InjectionResolver<Config> {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigInjectionResolver.class);

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
		} else if (Integer.class == injectee.getRequiredType()) {
			final Config annotation = injectee.getParent().getAnnotation(Config.class);

			if (annotation != null) {
				final String prop = annotation.value();

				return toInteger(configuration.getProperty(prop));
			}
		} else if (Long.class == injectee.getRequiredType()) {
			final Config annotation = injectee.getParent().getAnnotation(Config.class);

			if (annotation != null) {
				final String prop = annotation.value();

				return toLong(configuration.getProperty(prop));
			}
		}

		return null;
	}

	private Integer toInteger(final Object obj) {
		if (obj != null) {
			if (obj instanceof Integer)
				return (Integer) obj;
			else if (obj instanceof Long)
				return ((Long) obj).intValue();
			else {
				final String val = obj.toString();

				try {
					return Integer.parseInt(val);
				} catch (final NumberFormatException ex) {
					LOG.warn("Invalid number in settings: {}", val);
					return null;
				}
			}
		} else {
			return null;
		}
	}

	private Long toLong(final Object obj) {
		if (obj != null) {
			if (obj instanceof Long)
				return (Long) obj;
			else if (obj instanceof Integer)
				return ((Integer) obj).longValue();
			else {
				final String val = obj.toString();

				try {
					return Long.parseLong(val);
				} catch (final NumberFormatException ex) {
					LOG.warn("Invalid number in settings: {}", val);
					return null;
				}
			}
		} else {
			return null;
		}
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
