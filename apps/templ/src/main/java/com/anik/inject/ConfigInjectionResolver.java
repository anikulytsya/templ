package com.anik.inject;

import com.anik.annotations.Config;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.util.stream.Stream;
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
			final Config config = getConfigAnnotation(injectee);

			if (config != null) {
				final String prop = config.value();

				return (String) configuration.getProperty(prop);
			}
		} else if (Integer.class == injectee.getRequiredType()) {
			final Config config = getConfigAnnotation(injectee);

			if (config != null) {
				final String prop = config.value();

				return toInteger(configuration.getProperty(prop));
			}
		} else if (Long.class == injectee.getRequiredType()) {
			final Config config = getConfigAnnotation(injectee);

			if (config != null) {
				final String prop = config.value();

				return toLong(configuration.getProperty(prop));
			}
		}

		return null;
	}

	private Config getConfigAnnotation(final Injectee injectee) {
		final AnnotatedElement elem = injectee.getParent();

		if (elem instanceof Constructor) {
			final Constructor ctor = (Constructor) elem;
			final Annotation[] annotations = ctor.getParameterAnnotations()[injectee.getPosition()];

			if (annotations != null)
				return Stream.of(annotations)
					.filter(a -> a.annotationType().equals(Config.class))
					.map(a -> (Config) a)
					.findFirst()
					.orElse(null);
			else
				return null;
		} else {
			return elem.getAnnotation(Config.class);
		}
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
		return true;
	}

	@Override
	public boolean isMethodParameterIndicator() {
		return false;
	}
}
