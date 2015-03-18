package se.stonepath.senpai.backend.service;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import se.stonepath.senpai.backend.db.DatabaseHandler;
import se.stonepath.senpai.backend.db.model.ApplicationModel;
import se.stonepath.senpai.backend.json.model.ApplicationJsonModel;


@Path("/application")
public class ApplicationService {

	
	@Path("/add")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String registrate(String jsonData){			
		
			try {
			
			if(jsonData == null)
					throw new NullPointerException("jsondata empty!");
				
			DatabaseHandler dbHandler = new DatabaseHandler();
			
			String appCode = DatabaseHandler.generateUniqueID(dbHandler.getApplicationModelDao());
			ApplicationJsonModel applicationJsonModel = new Gson().fromJson(jsonData, ApplicationJsonModel.class);
			
			if(applicationJsonModel == null)
				throw new NullPointerException("Unable to parse required json data");
			
			
			
			ApplicationModel applicationModel = new ApplicationModel(appCode,applicationJsonModel.getApplicationName());
			
			dbHandler.getApplicationModelDao().create(applicationModel);
			dbHandler.close();
			
			return appCode;
			
			} catch (Exception e) {
				throw new BadRequestException(e);
			}
	
		
	}
	
	@Path("/get")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String get(@QueryParam("app") String appCode){
		try {
			DatabaseHandler dbHandler = new DatabaseHandler();
			
			ApplicationModel applicationModel = dbHandler.getApplicationModelDao().queryForId(appCode);
			
			dbHandler.close();
			
			return new Gson().toJson(applicationModel);
			
		} catch (Exception e) {
			throw new BadRequestException(e);
		}
	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public String list(){
		try {
			DatabaseHandler dbHandler = new DatabaseHandler();
			
			
			List<ApplicationModel> applications = dbHandler.getApplicationModelDao().queryForAll();
			
			dbHandler.close();
			
			return new Gson().toJson(applications);
			
		} catch (Exception e) {
			throw new BadRequestException(e);
		}
	}
	
	
	
	
	
	
}
