package com.ucac.vo;

public class WorkInfo {
	private int category;
	private String communityName;
	private int id;
	private String school;
	private int workState;
	public int getCategory() {
		return category;
	}
	public String getCommunityName() {
		return communityName;
	}
	public int getId() {
		return id;
	}
	public String getSchool() {
		return school;
	}
	public int getWorkState() {
		return workState;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public void setWorkState(int workState) {
		this.workState = workState;
	}
	
	@Override
	public String toString() {
		return "id:"+id+" |school:"+school+" |communityName:"+this.communityName+" |category:"+category+" |workState:"+workState;
	}
}
