package com.anik.swagger;

import io.swagger.v3.jaxrs2.Reader;
import io.swagger.v3.jaxrs2.ReaderListener;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("")
@OpenAPIDefinition
public class OpenApiUpdater implements ReaderListener {
	private static final Logger LOG = LoggerFactory.getLogger(OpenApiUpdater.class);

	@Override
	public void beforeScan(final Reader reader, final OpenAPI oapi) {
	}

	@Override
	public void afterScan(final Reader reader, final OpenAPI oapi) {
		oapi.getPaths().values().stream()
			.flatMap(p -> p.readOperations().stream())
			.map(o -> o.getResponses())
			.flatMap(r -> r.values().stream())
			.flatMap(r -> r.getContent().entrySet().stream())
			.filter(e -> e.getKey().equals("application/json"))
			.map(e -> e.getValue()) 
			.forEach(m -> {
				m.setSchema(wrapResponse(m.getSchema()));
			});
	}

	private Schema wrapResponse(final Schema schema) {
		Schema res = new Schema();

		res.type("object")
			.addProperties("error", new Schema().type("string"))
			.addProperties("data", schema);
		return res;
	}
}
