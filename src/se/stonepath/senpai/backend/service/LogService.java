package se.stonepath.senpai.backend.service;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Level;

import com.google.gson.Gson;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import se.stonepath.senpai.backend.db.DatabaseHandler;
import se.stonepath.senpai.backend.db.model.ApplicationModel;
import se.stonepath.senpai.backend.db.model.LogModel;
import se.stonepath.senpai.skeleton.LogSkeleton;



@Path("/log")
public class LogService {

	
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insert(String logData){
		
		LogSkeleton log = new Gson().fromJson(logData, LogSkeleton.class);
		
		try {
			DatabaseHandler dbHandler = new DatabaseHandler();
			ApplicationModel applicationModel = dbHandler.getApplicationModelDao().queryForId(log.getAppCode());
			
			dbHandler.getLogModelDao().create(new LogModel(log.getMessage(),applicationModel,log.getLogger(),log.getHost(), log.getLevel(),log.getTimestamp()));
			dbHandler.close();
		} catch (Exception e) {
			return e.getMessage();
		}
		
		
		return "1";
		
	}
	

	@Path("/clear")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String clear(@QueryParam("to") long toStamp){
		
		Integer recordsDeleted = 0;
		
		try {
			DatabaseHandler dbHandler = new DatabaseHandler();
			DeleteBuilder<LogModel, Integer> deleteBuilder = dbHandler.getLogModelDao().deleteBuilder();
			deleteBuilder.where().le("stamp", toStamp);
			recordsDeleted = deleteBuilder.delete();
			
			dbHandler.close();
		} catch (Exception e) {
			return e.getMessage();
		}
		return recordsDeleted.toString();
		
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String get(@QueryParam("offset") long offset,@QueryParam("limit") long limit, 
			@QueryParam("app") String appCode,
			@QueryParam("level") List<String> level){
		
		try {
			
			DatabaseHandler dbHandler = new DatabaseHandler();
			
			QueryBuilder<LogModel,Integer> queryBuilder = dbHandler.getLogModelDao().queryBuilder();
			
			
			
			//limit and orderby
			if( (offset != 0) && (limit != 0) ){
				queryBuilder = queryBuilder.offset(offset).limit(limit);
			}else if(limit != 0){
				queryBuilder = queryBuilder.limit(limit);
			}
			queryBuilder = queryBuilder.orderBy("stamp",false);
			
			//Initial query
			Where<LogModel,Integer> whereStatement = queryBuilder.where().isNotNull("application_id");
			
			//AppCode filter
			if(appCode != null){
				whereStatement = whereStatement.and().eq("application_id", appCode);
			}
			//Level filter
			if(level.size() > 0){
				Where<LogModel,Integer> levelWhere = whereStatement;	
				levelWhere.eq("level", Level.toLevel(level.get(0)));
				for(int i = 1 ; i < level.size();i++){
					levelWhere.or().eq("level", Level.toLevel(level.get(i)));
				}
				whereStatement = whereStatement.and(whereStatement, levelWhere);
			}
			
			
			List<LogModel> logModels = whereStatement.query();
			
			
			dbHandler.close();
			
			return new Gson().toJson(logModels);
			
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}
}
