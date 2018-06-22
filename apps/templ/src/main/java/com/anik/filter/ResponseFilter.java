package com.anik.filter;

import com.anik.model.ServletResponse;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ResponseFilter implements ContainerResponseFilter {
	private static final Logger LOG = LoggerFactory.getLogger(ResponseFilter.class);

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(
			  final ContainerRequestContext requestContext,
			  final ContainerResponseContext responseContext) throws IOException {
		if (shouldBeWrapped())
			responseContext.setEntity(wrap(responseContext.getEntity()));
	}

	private boolean shouldBeWrapped() {
		//avoid wraping for open api response
		return !resourceInfo.getResourceClass().equals(OpenApiResource.class);
	}

	private ServletResponse wrap(final Object data) {
		final ServletResponse response = new ServletResponse();

		response.setData(data);
		return response;
	}
}
