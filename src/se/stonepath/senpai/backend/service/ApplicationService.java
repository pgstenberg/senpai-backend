package se.stonepath.senpai.backend.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import se.stonepath.senpai.backend.db.DatabaseHandler;
import se.stonepath.senpai.backend.db.model.ApplicationModel;


@Path("/app")
public class ApplicationService {

	
	@Path("/reg")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String registrate(@Context HttpServletRequest request){			
		
		try {
			DatabaseHandler dbHandler = new DatabaseHandler();
			String remoteIp = request.getRemoteAddr();
			String appCode = DatabaseHandler.generateUniqueID(dbHandler.getApplicationModelDao());
			ApplicationModel applicationModel = new ApplicationModel(appCode,remoteIp);
			
			dbHandler.getApplicationModelDao().create(applicationModel);
			dbHandler.close();
			
			return appCode;
			
		} catch (SQLException | ClassNotFoundException e) {
			return e.getMessage();
		}
		
	}
	
	
	
	
}
