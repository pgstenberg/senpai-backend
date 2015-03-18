package se.stonepath.senpai.backend.service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.rmi.Naming;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import se.stonepath.senpai.backend.db.DatabaseHandler;
import se.stonepath.senpai.backend.db.model.ApplicationModel;
import se.stonepath.senpai.backend.db.model.RemoteModel;
import se.stonepath.senpai.backend.json.model.RemoteCallJsonModel;
import se.stonepath.senpai.backend.json.model.RemoteJsonModel;
import se.stonepath.senpai.backend.json.model.RemoteMethodJsonModel;
import se.stonepath.senpai.backend.json.model.RemoteMethodJsonModel.RemoteMethodParameterJsonModel;

@Path("/remote")
public class RemoteService {

	@Path("/method/call")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String call(String jsonData) throws Exception{
		
		RemoteCallJsonModel callModel = new Gson().fromJson(jsonData, RemoteCallJsonModel.class);
		
		DatabaseHandler dbHandler = new DatabaseHandler();
		RemoteModel remoteModel = dbHandler.getRemoteModelDao().queryForId(callModel.getRemoteCode());
		
		
		Class<?> cls = Class.forName(remoteModel.getRmiInterface());
		Object obj = (cls.cast(Naming.lookup(remoteModel.getRmiUrl())));
		
		
		Object[] params = new Object[callModel.getParameters().length];
		Class<?>[] paramClasses = new Class<?>[callModel.getParameters().length];
		for(int i = 0; i < callModel.getParameters().length;i++){
			Class<?> paramCls = Class.forName(callModel.getParameters()[i].getClassType());
			Object value = callModel.getParameters()[i].getValue();
			if(paramCls.equals(Integer.class)){
				value = Integer.parseInt((String)value);
			}
			
			Object paramObj = paramCls.cast(value);
			params[i] = paramObj;
			paramClasses[i] = paramCls;
		}
		Method method = cls.getDeclaredMethod(callModel.getMethodName(),paramClasses);
		
		
		return new Gson().toJson(method.invoke(obj,params));
		
	}
	
	@Path("/method/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String listMethods(@QueryParam("rmi") String rmiCode) throws Exception{
		DatabaseHandler dbHandler = new DatabaseHandler();
		RemoteModel remoteModel = dbHandler.getRemoteModelDao().queryForId(rmiCode);	
		Class<?> cls = Class.forName(remoteModel.getRmiInterface());
		
		ArrayList<RemoteMethodJsonModel> methods = new ArrayList<RemoteMethodJsonModel>();
		for(Method method : cls.getMethods()){
			RemoteMethodJsonModel jsonMethod = new RemoteMethodJsonModel(method.getName(),method.getReturnType().getName());
			ArrayList<RemoteMethodParameterJsonModel> parameters = new ArrayList<RemoteMethodParameterJsonModel>(); 
			for(Parameter parameter : method.getParameters()){
				parameters.add(jsonMethod.new RemoteMethodParameterJsonModel(parameter.getName(),parameter.getAnnotatedType().getType().getTypeName()));
			}
			jsonMethod.setParameters(parameters.toArray(new RemoteMethodParameterJsonModel[parameters.size()]));
			
			methods.add(jsonMethod);
		}
		
		return new Gson().toJson(methods);
	}
	
	
	@Path("/list")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String listRemotes(@QueryParam("app") String applicationCode) throws Exception{
		DatabaseHandler dbHandler = new DatabaseHandler();
		if(applicationCode != null)
			return new Gson().toJson(dbHandler.getRemoteModelDao().queryBuilder().where().eq("application_id",applicationCode).query());
		
		return new Gson().toJson(dbHandler.getRemoteModelDao().queryForAll());
		
	}
	
	@Path("/add")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addRemote(String jsonData) throws Exception{
		
		RemoteJsonModel rmiJsonModel = new Gson().fromJson(jsonData, RemoteJsonModel.class);
		
		DatabaseHandler dbHandler = new DatabaseHandler();
		Dao<RemoteModel,String> dao = dbHandler.getRemoteModelDao();
		
		String rmiCode = DatabaseHandler.generateUniqueID(dao);
		ApplicationModel applicationModel = dbHandler.getApplicationModelDao().queryForId(rmiJsonModel.getApplicationCode());
		
		RemoteModel rmiModel = new RemoteModel(rmiCode, applicationModel, rmiJsonModel.getRmiUrl(),rmiJsonModel.getRmiInterface());
		
		dao.create(rmiModel);
		
		return rmiCode;
	}
}
