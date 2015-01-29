package se.stonepath.senpai.backend;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloWorld {

	@Path("/world")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String world(){
		return "<p>Hello World</p>";
	}
}
