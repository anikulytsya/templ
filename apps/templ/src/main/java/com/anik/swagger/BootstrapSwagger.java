package com.anik.swagger;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BootstrapSwagger extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		final OpenAPI oas = new OpenAPI();
		final Components components = new Components();
		final SecurityScheme apiKey = createApiKeySecurityScheme();
		final Info info = new Info()
				  .title("Swagger Sample App - independent config exposed by dedicated servlet")
				  .description("This is a sample server Petstore server.  You can find out more about Swagger "
							 + "at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).  For this sample, "
							 + "you can use the api key `special-key` to test the authorization filters.")
				  .termsOfService("http://swagger.io/terms/")
				  .contact(new Contact()
							 .email("apiteam@swagger.io"))
				  .license(new License()
							 .name("Apache 2.0")
							 .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

		oas.info(info);
		oas.schemaRequirement(apiKey.getName(), apiKey);
		SecurityRequirement sr = new SecurityRequirement();
		sr.addList(apiKey.getName());
		oas.security(Arrays.asList(sr));
		//oas.components(components
		//	.addSecuritySchemes("apiKey", createApiKeySecurityScheme())
		//);
		oas.addServersItem(getServer(config));

		SwaggerConfiguration oasConfig = new SwaggerConfiguration()
				  .openAPI(oas)
				  .resourcePackages(Stream.of("com.anik").collect(Collectors.toSet()));

		// or
/*
    try {
      new GenericOpenApiContext().openApiConfiguration(oasConfig).init();
    } catch (OpenApiConfigurationException e) {
      e.printStackTrace();
    }
		 */
		try {
			new JaxrsOpenApiContextBuilder()
					  .servletConfig(config)
					  //.ctxId(config.getServletName())
					  .openApiConfiguration(oasConfig)
					  .buildContext(true);
		} catch (OpenApiConfigurationException e) {
			throw new ServletException(e.getMessage(), e);
		}
	}

	private Server getServer(final ServletConfig config) {
		final Server server = new Server();

		
		final String context = config.getServletContext().getContextPath();
		if (context == null || context.isEmpty())
			server.setUrl("http://localhost:8084/api/");
		else if (context.startsWith("/"))
			server.setUrl("http://localhost:8084" + context +  "/api/");
		else
			server.setUrl("http://localhost:8084" + context +  "/api/");

		return server;
	}

	private SecurityScheme createApiKeySecurityScheme() {
		final SecurityScheme securityScheme = new SecurityScheme();

		securityScheme.setName("X-Api-Key");
		securityScheme.setType(SecurityScheme.Type.APIKEY);
		securityScheme.setIn(SecurityScheme.In.HEADER);
		return securityScheme;
	}
}
