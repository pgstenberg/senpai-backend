package se.stonepath.senpai.backend.db;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;

import se.stonepath.senpai.backend.db.model.ApplicationModel;
import se.stonepath.senpai.backend.db.model.DelegateModel;
import se.stonepath.senpai.backend.db.model.LogModel;

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
	private Dao<DelegateModel,String> delegateModelDao;
	
	
	public DatabaseHandler() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		
		dbConnection = new JdbcConnectionSource("jdbc:sqlite:" + DATABASE_INSTANCE);
	
		logModelDao = DaoManager.createDao(dbConnection, LogModel.class);
		applicationModelDao = DaoManager.createDao(dbConnection, ApplicationModel.class);
		delegateModelDao = DaoManager.createDao(dbConnection, DelegateModel.class);
	}
	
	public void close() throws SQLException{
		dbConnection.close();
	}
	
	
	public void installDatabase() throws SQLException{
		TableUtils.createTable(dbConnection, LogModel.class);
		TableUtils.createTable(dbConnection, ApplicationModel.class);
		TableUtils.createTable(dbConnection, DelegateModel.class);
		
	}
	
	public Dao<LogModel,Integer> getLogModelDao(){
		return logModelDao;
	}

	public Dao<ApplicationModel,String> getApplicationModelDao(){
		return applicationModelDao;
	}
	public Dao<DelegateModel,String> getDelegateModelDao(){
		return delegateModelDao;
	}
	
	public static String generateUniqueID(Dao<?, String> dao) throws SQLException{
		HashGenerator appCodeGenerator = new HashGenerator();
		
		String appCode = appCodeGenerator.generateHash();
		
		while(dao.idExists(appCode)){
			appCode = appCodeGenerator.generateHash();
		}
		
		return appCode;
	}
	
	private static class HashGenerator {
		  private SecureRandom random = new SecureRandom();
	  
		  public String generateHash() {
		    return new BigInteger(130, random).toString(32);
		  }
	}
	
}
