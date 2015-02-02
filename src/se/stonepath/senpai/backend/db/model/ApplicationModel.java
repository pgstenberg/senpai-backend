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
	private int timestamp;
	
	
	public ApplicationModel(String appCode,String requester) {
		this.requester = requester;
		this.appCode = appCode;
		this.timestamp = (int)(System.currentTimeMillis() / 1000L);
	}
	
	public ApplicationModel(){
		
	}
	
}
