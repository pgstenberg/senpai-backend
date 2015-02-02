package se.stonepath.senpai.backend.db;




public class LogHandler {

	private DatabaseHandler dbHandler;
	
	public LogHandler(DatabaseHandler dbHandler) {
		this.dbHandler = dbHandler;
	}
	/*
	private String constructInsertQuery(LogSkeleton log){
		return "INSERT INTO logs (logger, application, host, level, timestamp, message) VALUES "
				+ "('" + log.getLogger() + "',"
				+ "'" + log.getApplication() + "',"
				+ "'" + log.getHost() + "',"
				+ "'" + log.getLevel() + "',"
				+ "'" + log.getTimestamp() + "',"
				+ "'" + log.getMessage() + "')";
		
	}
	private String constructGetQuery(int from,int to,List<String> filter){
		
		String select = "*";
		
		if(!filter.isEmpty()){
			StringBuilder selectBuilder = new StringBuilder();
			
			for(int i = 0; i < filter.size();i++){
				
				if(i < (filter.size() - 1))
					selectBuilder.append(filter.get(i) + ",");
				else
					selectBuilder.append(filter.get(i));
				
			}
			
			select = selectBuilder.toString();
		}
		
		System.out.println("SELECTPART: " + select);
		
		
		if(from == 0 && to > 0){
			return "SELECT " + select + " FROM logs LIMIT " + to;
		}else if(from > 0 && to > 0){
			return "SELECT " + select + " FROM logs LIMIT " + to + " OFFSET " + from;
		}else{
			return "SELECT " + select + " FROM logs";
		}
	}
	*/
	/*
	public void insertLog(LogSkeleton log) throws SQLException{
		new SQLInsertClause(conn, configuration, survey)
	    .columns(survey.id, survey.name)
	    .values(3, "Hello").execute();
	}
	
	
	public ArrayList<LogSkeleton> receiveLogs(int from, int to, List<String> filter) throws SQLException{
		
		ArrayList<LogSkeleton> returnList = new ArrayList<LogSkeleton>();
		
		Statement stmt = dbConnection.createStatement();
		ResultSet rs = stmt.executeQuery(constructGetQuery(from, to, filter));
		
		while (rs.next()) {
			  String application = rs.getString("application");
			  String host = rs.getString("host");
			  String logger = rs.getString("logger");
			  String level = rs.getString("level");
			  String message = rs.getString("message");
			  int stamp = rs.getInt("timestamp");
			  
			  returnList.add(new LogSkeleton(message, level, logger, application,host,stamp));
		}
		
		return returnList;
	}
	*/
}
