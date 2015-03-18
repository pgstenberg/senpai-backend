package se.stonepath.senpai.backend.db.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "applications")
public class ApplicationModel {

	
	@DatabaseField(id=true)
	private String application_id;
	
	@DatabaseField
	private String applicationName;
	
	public ApplicationModel(){
		
	}
	public ApplicationModel(String applicationCode,String applicationName) {
		this.application_id = applicationCode;
		this.applicationName = applicationName;
	}

	public String getApplicationName() {
		return applicationName;
	}
	
	
}
