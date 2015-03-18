package se.stonepath.senpai.backend.db;


import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;

import se.stonepath.senpai.backend.db.model.ApplicationModel;
import se.stonepath.senpai.backend.db.model.LogModel;
import se.stonepath.senpai.backend.db.model.RemoteModel;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHandler {

	public final static String DATABASE_INSTANCE = "senpai-database.db";
	
	private ConnectionSource dbConnection;
	
	private Dao<LogModel,Integer> logModelDao;
	private Dao<ApplicationModel,String> applicationModelDao;
	private Dao<RemoteModel,String> remoteModelDao;
	
	public DatabaseHandler() throws Exception{
		Class.forName("org.sqlite.JDBC");
		
		dbConnection = new JdbcConnectionSource("jdbc:sqlite:" + DATABASE_INSTANCE);
		
		if(!new File(DATABASE_INSTANCE).exists())
			installDatabase();
			
		
	
		logModelDao = DaoManager.createDao(dbConnection, LogModel.class);
		applicationModelDao = DaoManager.createDao(dbConnection, ApplicationModel.class);
		remoteModelDao = DaoManager.createDao(dbConnection, RemoteModel.class);
		
	}
	
	public void close() throws SQLException{
		dbConnection.close();
	}
	
	
	private void installDatabase() throws Exception{
		TableUtils.createTable(dbConnection, LogModel.class);
		TableUtils.createTable(dbConnection, ApplicationModel.class);
		TableUtils.createTable(dbConnection, RemoteModel.class);
		
	}
	
	public Dao<LogModel,Integer> getLogModelDao(){
		return logModelDao;
	}

	public Dao<ApplicationModel,String> getApplicationModelDao(){
		return applicationModelDao;
	}
	public Dao<RemoteModel,String> getRemoteModelDao(){
		return remoteModelDao;
	}
	
	
	public static String generateUniqueID(Dao<?, String> dao) throws SQLException{
		RandomHashGenerator appCodeGenerator = new RandomHashGenerator();
		
		String appCode = appCodeGenerator.generateHash();
		
		while(dao.idExists(appCode)){
			appCode = appCodeGenerator.generateHash();
		}
		
		return appCode;
	}
	
	
	private static class RandomHashGenerator {
		  private SecureRandom random = new SecureRandom();
	  
		  public String generateHash() {
		    return new BigInteger(130, random).toString(32);
		  }
	}
	
}
