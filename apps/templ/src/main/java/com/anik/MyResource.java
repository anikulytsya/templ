package com.anik;

import com.anik.model.User;
import com.anik.service.Service;
import com.anik.service.Service2;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("myresource")
//https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations
@OpenAPIDefinition(
	info = @Info(
		title = "MyResource title",
		version = "1.0",
		description = "My Resource API",
		contact = @Contact(url = "http://myresource.com", name = "Andriy N.", email = "andriy@myresource.com")
	),
	tags = {
		@Tag(name = "Tag1", description = "Tag1 description"),
		@Tag(name = "Tag2", description = "Tag2 description"),},
	security = {
		@SecurityRequirement(name = "req 1", scopes = {"a", "b"}),
		@SecurityRequirement(name = "req 2", scopes = {"b", "c"})
	}
)
public class MyResource {
	private static final Logger LOG = LoggerFactory.getLogger(MyResource.class);
	@Inject
	private Service service;
	@Inject
	private Service2 service2;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Get it", description = "Get it as text")
	public Response getIt() {
		final String res = service.getData();

		LOG.info("test");
		return Response.ok(res).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("user")
	@Operation(summary = "Get User summary",
		description = "Get User description", tags = {"Tag1"})
	public User getUser() {
		final User user = new User();

		user.setFirstName("xxx");
		user.setLastName("yyy");
		user.setDob(LocalDate.now());
		return user;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("user/{id}")
	@Operation(summary = "Get User by id summary",
		description = "Get User by id description", tags = {"Tag1"})
	public User getUserById(
		@PathParam("id") final Integer id
	) {
		final User user = new User();

		user.setFirstName("xxx" + id);
		user.setLastName("yyy");
		user.setDob(LocalDate.now());
		return user;
	}
}
