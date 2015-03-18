package se.stonepath.senpai.backend.db.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "remotes")
public class RemoteModel{
	
	@DatabaseField(id=true)
	private String remote_id;

	@DatabaseField(foreign = true,foreignAutoRefresh=true)
	private ApplicationModel application;
	
	@DatabaseField(uniqueCombo = true)
	private String rmiUrl;
	
	@DatabaseField(uniqueCombo = true)
	private String rmiInterface;
	
	public RemoteModel() {
	}
	
	
	public RemoteModel(String remoteCode,ApplicationModel application,String rmiUrl,String rmiInterface){
		this.remote_id = remoteCode;
		this.application = application;
		this.rmiUrl = rmiUrl;
		this.rmiInterface = rmiInterface;
	}


	public ApplicationModel getApplication() {
		return application;
	}


	public String getRmiUrl() {
		return rmiUrl;
	}


	public String getRmiInterface() {
		return rmiInterface;
	}
	
	
	
}
