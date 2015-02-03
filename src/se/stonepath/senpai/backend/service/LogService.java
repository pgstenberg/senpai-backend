package se.stonepath.senpai.backend.service;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import se.stonepath.senpai.backend.db.DatabaseHandler;
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
			dbHandler.getLogModelDao().create(new LogModel(log));
			dbHandler.close();
		} catch (Exception e) {
			return e.getMessage();
		}
		
		
		return "1";
		
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
			queryBuilder = queryBuilder.orderBy("stamp",true);
			
			//Initial query
			Where<LogModel,Integer> whereStatement = queryBuilder.where().isNotNull("appCode");
			
			//AppCode filter
			if(appCode != null){
				whereStatement = whereStatement.and().eq("appCode", appCode);
			}
			//Level filter
			if(level.size() > 0){
				Where<LogModel,Integer> levelWhere = whereStatement;	
				levelWhere.eq("level", LogSkeleton.Level.valueOf(level.get(0)));
				for(int i = 1 ; i < level.size();i++){
					levelWhere.or().eq("level", LogSkeleton.Level.valueOf(level.get(i)));
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
