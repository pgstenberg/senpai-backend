package se.stonepath.senpai.backend.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import se.stonepath.senpai.backend.db.DatabaseHandler;
import se.stonepath.senpai.backend.db.model.ApplicationModel;


@Path("/app")
public class ApplicationService {

	
	@Path("/add")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String registrate(@QueryParam("application") String application,@Context HttpServletRequest request){			
		
		try {
			DatabaseHandler dbHandler = new DatabaseHandler();
			String remoteIp = request.getRemoteAddr();
			String appCode = DatabaseHandler.generateUniqueID(dbHandler.getApplicationModelDao());
			ApplicationModel applicationModel = new ApplicationModel(appCode,application,remoteIp);
			
			dbHandler.getApplicationModelDao().create(applicationModel);
			dbHandler.close();
			
			return appCode;
			
		} catch (SQLException | ClassNotFoundException e) {
			return e.getMessage();
		}
		
	}
	
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list(){
		try {
			DatabaseHandler dbHandler = new DatabaseHandler();
			
			List<ApplicationModel> applications = dbHandler.getApplicationModelDao().queryForAll();
			
			dbHandler.close();
			
			return new Gson().toJson(applications);
			
		} catch (SQLException | ClassNotFoundException e) {
			return e.getMessage();
		}
	}
	
	
	
	
}
