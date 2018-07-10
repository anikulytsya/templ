package com.anik;

import com.anik.model.User;
import com.anik.service.Service;
import com.anik.service.Service2;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
		@Tag(name = "Tag2", description = "Tag2 description")}
)
@Produces(MediaType.APPLICATION_JSON)
public class MyResource {
	private static final Logger LOG = LoggerFactory.getLogger(MyResource.class);
	@Inject
	private Service service;
	@Inject
	private Service2 service2;
	@Inject
	private PersistenceManager pm;

	@GET
	@Operation(summary = "Get it", description = "Get it as text")
	public Response getIt() {
		final String res = service.getData();

		LOG.info("test");
		return Response.ok(res).build();
	}

	@GET
	@Path("user")
	@Operation(summary = "Get User summary",
		description = "Get User description", tags = {"Tag1"})
	public User getUser() {
		final User user = new User();

		user.setFirstName("xxx");
		user.setLastName("yyy");
		user.setDob(LocalDate.now());
		user.setInstant(Instant.now());
		user.setLocalDateTime(LocalDateTime.now());
		user.setOffsetDateTime(OffsetDateTime.now());
		return user;
	}

	@GET
	@Path("user/list")
	@Operation(summary = "Get User list",
		description = "Get User list", tags = {"Tag1"})
	public List<User> getUserList() {
		final List<User> res = new ArrayList<>();
		final User user = new User();

		user.setFirstName("xxx");
		user.setLastName("yyy");
		user.setDob(LocalDate.now());
		user.setInstant(Instant.now());
		user.setLocalDateTime(LocalDateTime.now());
		user.setOffsetDateTime(OffsetDateTime.now());
		res.add(user);
		return res;
	}

	@GET
	@Path("user/map")
	@Operation(summary = "Get User map",
		description = "Get User map", tags = {"Tag1"})
	public Map<String, User> getUserMap() {
		final Map<String, User> res = new HashMap<>();
		final User user = new User();

		user.setFirstName("xxx");
		user.setLastName("yyy");
		user.setDob(LocalDate.now());
		user.setInstant(Instant.now());
		user.setLocalDateTime(LocalDateTime.now());
		user.setOffsetDateTime(OffsetDateTime.now());
		res.put("first", user);
		return res;
	}

	@GET
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
		user.setInstant(Instant.now());
		user.setLocalDateTime(LocalDateTime.now());
		user.setOffsetDateTime(OffsetDateTime.now());
		return user;
	}

	@GET
	@Path("exception")
	@Operation(summary = "This api throws an exception",
		description = "Get User by id description", tags = {"Tag1"})
	public Object exception(
			@QueryParam("val") final Integer val) {
		throw new RuntimeException(val.toString());
	}
}
