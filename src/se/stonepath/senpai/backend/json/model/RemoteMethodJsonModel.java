package se.stonepath.senpai.backend.json.model;

public class RemoteMethodJsonModel {
	public String methodName;
	public String returnClassType;
	public RemoteMethodParameterJsonModel[] parameters;
	
	public RemoteMethodJsonModel(String methodName,String returnClassType) {
		this.methodName = methodName;
		this.returnClassType = returnClassType;
	}
	
	public void setParameters(RemoteMethodParameterJsonModel[] parameters){
		this.parameters = parameters;
	}
	
	public class RemoteMethodParameterJsonModel{
		public String parameterName;
		public String classType;
		
		public RemoteMethodParameterJsonModel(String parameterName,String classType) {
			this.parameterName = parameterName;
			this.classType = classType;
		}
		
	}
}
