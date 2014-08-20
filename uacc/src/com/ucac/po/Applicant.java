package com.ucac.po;

import java.io.Serializable;

import com.ucac.anotation.LuceneField;
import com.ucac.anotation.LucenePo;

@LucenePo
public class Applicant implements Serializable {
	private int category;
	@LuceneField
	private String communityName;
	@LuceneField
	private int id;
	private String password;
	@LuceneField
	private String school;
	private String teacherEmail;
	private String teacherName;
	private String teacherPhone;
	@LuceneField
	private String username;
	private int status;
	public int getCategory() {
		return category;
	}
	public String getCommunityName() {
		return communityName;
	}
	public int getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public String getSchool() {
		return school;
	}
	public String getTeacherEmail() {
		return teacherEmail;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public String getTeacherPhone() {
		return teacherPhone;
	}
	public String getUsername() {
		return username;
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
	public void setPassword(String password) {
		this.password = password;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
