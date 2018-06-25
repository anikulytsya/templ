package com.anik.swagger;

import io.swagger.v3.jaxrs2.Reader;
import io.swagger.v3.jaxrs2.ReaderListener;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition
public class OpenApiUpdater implements ReaderListener {
	private static final Logger LOG = LoggerFactory.getLogger(OpenApiUpdater.class);

	@Override
	public void beforeScan(final Reader reader, final OpenAPI oapi) {
		LOG.warn("before scan");
	}

	@Override
	public void afterScan(final Reader reader, final OpenAPI oapi) {
		LOG.warn("after scan");
	}
}
