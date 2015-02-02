package se.stonepath.senpai.backend.db.model;

import se.stonepath.senpai.skeleton.delegate.DelegateRequestSkeleton;
import se.stonepath.senpai.skeleton.delegate.DelegateStatusSkeleton.Status;

import com.j256.ormlite.field.DatabaseField;

public class DelegateModel {
	
	
	public DelegateModel() {
	}
	public DelegateModel(String delegateCode,String appCode,String command){
		this.appCode = appCode;
		this.delegateCode = delegateCode;
		this.command = command;
		this.status = Status.PENDING;
		this.timestamp = (int)(System.currentTimeMillis() / 1000L);
	}
	public DelegateModel(String delegateCode, DelegateRequestSkeleton delegateSkeleton){
		this(delegateCode,delegateSkeleton.getAppCode(),delegateSkeleton.getCommand());
	}

	@DatabaseField(id=true)
	private String delegateCode;
	
	@DatabaseField
	private String appCode;
	@DatabaseField
	private String command;
	@DatabaseField
	private Status status;
	@DatabaseField
	private int timestamp;
	
	
	
}
