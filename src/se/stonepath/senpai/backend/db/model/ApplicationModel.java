package se.stonepath.senpai.backend.db.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "apps")
public class ApplicationModel {

	
	@DatabaseField(id=true)
	private String appCode;
	
	@DatabaseField
	private String requester;
	
	@DatabaseField
	private String application;
	
	@DatabaseField
	private int timestamp;
	
	
	public ApplicationModel(String appCode,String application,String requester) {
		this.requester = requester;
		this.appCode = appCode;
		this.application = application;
		this.timestamp = (int)(System.currentTimeMillis() / 1000L);
	}
	
	public ApplicationModel(){
		
	}
	
}
