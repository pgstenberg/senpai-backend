package se.stonepath.senpai.backend.db.model;




import se.stonepath.senpai.skeleton.LogSkeleton;
import se.stonepath.senpai.skeleton.LogSkeleton.Level;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "logs")
public class LogModel{

	//AUTO INCREMENT
	
	@DatabaseField(generatedId = true,allowGeneratedIdInsert=true)
    private int ID;
	
	@DatabaseField
	private String message;
	@DatabaseField
	private String appCode;
	@DatabaseField
	private String logger;
	@DatabaseField
	private Level level;
	@DatabaseField
	private int stamp;
	
	public LogModel(LogSkeleton logSkeleteon) {
		this.ID = 0;
		this.message = logSkeleteon.getMessage();
		this.appCode = logSkeleteon.getAppCode();
		this.logger = logSkeleteon.getLogger();
		this.stamp = (int) logSkeleteon.getTimestamp();
		this.level = logSkeleteon.getLevel();
	}
	
	public LogModel() {
	}
	
}
