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
import se.stonepath.senpai.skeleton.delegate.DelegateStatusSkeleton;

import com.google.gson.Gson;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

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
	
	@Path("/status")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String status(String jsonData){
		
		try{
			DelegateStatusSkeleton delegateStatus = new Gson().fromJson(jsonData, DelegateStatusSkeleton.class);
			DatabaseHandler dbHandler = new DatabaseHandler();
					
			UpdateBuilder<DelegateModel,String> updateBuilder = dbHandler.getDelegateModelDao().updateBuilder();
			updateBuilder.updateColumnValue("status", delegateStatus.getStatus());
			updateBuilder.where().eq("delegateCode", delegateStatus.getDelegateCode());
			updateBuilder.update();
			
			dbHandler.close();
			
			return "1";
			
			
		}catch(Exception e){
			return e.getMessage();
		}
		
	}
	
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String pendingDelegates(@QueryParam("app") String appCode,@QueryParam("status") String status){
		
		try{
			DatabaseHandler dbHandler = new DatabaseHandler();
			
			Where<DelegateModel,String> where = dbHandler.getDelegateModelDao().queryBuilder().where().eq("appCode",appCode);
			
			if(status != null)
				where.and().eq("status", DelegateStatusSkeleton.Status.valueOf(status));
			
			List<DelegateModel> delagates = where.query();
					
			dbHandler.close();
			
			return new Gson().toJson(delagates);
			
			
		}catch(Exception e){
			return e.getMessage();
		}
		
	}
}
