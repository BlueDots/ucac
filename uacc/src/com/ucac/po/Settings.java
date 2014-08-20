package com.ucac.po;

import java.io.Serializable;
import java.util.Date;

public class Settings  implements Serializable{
	private Date applyBegin;
	private Date applyEnd;
	private Date appraiseBegin;
	private Date appraiseEnd;
	private int document;
	private int id;
	private int picture;
	private int touristState;
	private int video;
	public Date getApplyBegin() {
		return applyBegin;
	}
	public Date getApplyEnd() {
		return applyEnd;
	}
	public Date getAppraiseBegin() {
		return appraiseBegin;
	}
	public Date getAppraiseEnd() {
		return appraiseEnd;
	}
	public int getDocument() {
		return document;
	}
	public int getId() {
		return id;
	}
	public int getPicture() {
		return picture;
	}
	public int getTouristState() {
		return touristState;
	}
	public int getVideo() {
		return video;
	}
	public void setApplyBegin(Date applyBegin) {
		this.applyBegin = applyBegin;
	}
	public void setApplyEnd(Date applyEnd) {
		this.applyEnd = applyEnd;
	}
	public void setAppraiseBegin(Date appraiseBegin) {
		this.appraiseBegin = appraiseBegin;
	}
	public void setAppraiseEnd(Date appraiseEnd) {
		this.appraiseEnd = appraiseEnd;
	}
	public void setDocument(int document) {
		this.document = document;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPicture(int picture) {
		this.picture = picture;
	}
	public void setTouristState(int touristState) {
		this.touristState = touristState;
	}
	public void setVideo(int video) {
		this.video = video;
	}
	
	
}
