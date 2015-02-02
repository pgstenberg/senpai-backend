package se.stonepath.senpai.backend.service;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import se.stonepath.senpai.backend.db.DatabaseHandler;

@Path("/")
public class CoreService {
	
	
	@Path("/db/install")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String install(){
		
		if(!new File(DatabaseHandler.DATABASE_INSTANCE).exists()){
			
		try{
		DatabaseHandler dbHandler = new DatabaseHandler();
		dbHandler.installDatabase();
		dbHandler.close();
		}catch(Exception e){
			return e.getMessage();
		}
		
		}else{
			return "Database already exists! " + new File(DatabaseHandler.DATABASE_INSTANCE).getAbsolutePath();
		}
		
		return "1";
	}
	
}
