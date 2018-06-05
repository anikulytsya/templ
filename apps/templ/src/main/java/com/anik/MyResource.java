package com.anik;

import com.anik.model.User;
import java.time.LocalDate;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt() {
        return Response.ok("Got it!").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	 @Path("user")
    public User getUser() {
		final User user = new User();

		user.setFirstName("xxx");
		user.setLastName("yyy");
		user.setDob(LocalDate.now());
		return user;
    }
}
