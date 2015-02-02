package se.stonepath.senpai.backend.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import se.stonepath.senpai.backend.db.DatabaseHandler;
import se.stonepath.senpai.backend.db.model.DelegateModel;
import se.stonepath.senpai.skeleton.delegate.DelegateRequestSkeleton;

import com.google.gson.Gson;

@Path("/delegate")
public class DelegateService {

	
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(String jsonData){	
		
		try{
			DelegateRequestSkeleton delegateRequest = new Gson().fromJson(jsonData, DelegateRequestSkeleton.class);
			DatabaseHandler dbHandler = new DatabaseHandler();
			String delegateCode = DatabaseHandler.generateUniqueID(dbHandler.getDelegateModelDao());
			dbHandler.getDelegateModelDao().create(new DelegateModel(delegateCode,delegateRequest));
					
			dbHandler.close();
			
			return delegateCode;
			
			
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
	@Path("/pending")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String pendingDelegates(@QueryParam("app") String appCode){
		
		try{
			DatabaseHandler dbHandler = new DatabaseHandler();
			
			List<DelegateModel> delagates = dbHandler.getDelegateModelDao().queryBuilder().where().eq("appCode",appCode).query();
					
			dbHandler.close();
			
			return new Gson().toJson(delagates);
			
			
		}catch(Exception e){
			return e.getMessage();
		}
		
	}
}
