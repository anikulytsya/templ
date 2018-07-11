package com.anik;

import com.anik.model.ServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<Throwable> {
	@Override
	public Response toResponse(final Throwable e) {
		final ServletResponse resp = new ServletResponse();

		if (e instanceof WebApplicationException) {
			final Response r = ((WebApplicationException) e).getResponse();

			resp.setCode(r.getStatus());
			resp.setError(e.getMessage());
			return Response.status(r.getStatus()).entity(resp).build();
		} else {
			resp.setError(e.getMessage());
			resp.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
			return Response.serverError().entity(resp).build();
		}
	}
}
