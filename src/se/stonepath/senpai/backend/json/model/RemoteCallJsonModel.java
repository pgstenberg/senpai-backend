package se.stonepath.senpai.backend.json.model;

public class RemoteCallJsonModel {

	private String methodName;
	private String remoteCode;
	private RemoteCallParameterJsonModel[] parameters;
	
	public RemoteCallJsonModel(String remoteCode,String methodName) {
		this.methodName = methodName;
	}
	
	public void setParameters(RemoteCallParameterJsonModel[] parameters){
		this.parameters = parameters;
	}
	
	
	
	
	public String getMethodName() {
		return methodName;
	}
	

	public String getRemoteCode() {
		return remoteCode;
	}

	public RemoteCallParameterJsonModel[] getParameters() {
		return parameters;
	}




	public class RemoteCallParameterJsonModel{
		private Object value;
		private String classType;
		
		public RemoteCallParameterJsonModel(Object value) {
			this.value = value;
			this.classType = value.getClass().getTypeName();
		}

		public Object getValue() {
			return value;
		}

		public String getClassType() {
			return classType;
		}
		
		
		
	}
}
