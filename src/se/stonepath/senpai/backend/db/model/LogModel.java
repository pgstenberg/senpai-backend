package se.stonepath.senpai.backend.db.model;




import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "logs")
public class LogModel{

	@DatabaseField(generatedId = true,allowGeneratedIdInsert=true)
    private int ID;
	
	@DatabaseField
	private String message;
	@DatabaseField(foreign = true)
	private ApplicationModel application;
	@DatabaseField
	private String logger;
	@DatabaseField
	private String host;
	@DatabaseField
	private String level;
	@DatabaseField
	private long stamp;
	
	public LogModel(String message,ApplicationModel application,String logger,String host, String level,long stamp) {
		this.ID = 0;
		this.message = message;
		this.application = application;
		this.logger = logger;
		this.stamp = stamp;
		this.level = level;
		this.host = host;
	}
	
	public LogModel() {
	}
	
}
