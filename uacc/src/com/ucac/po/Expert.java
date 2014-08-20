package com.ucac.po;

import java.io.Serializable;

import com.ucac.anotation.LuceneField;
import com.ucac.anotation.LucenePo;
 
@LucenePo
public class Expert  implements Serializable{
	private int category;
	private String expertEmail;
	@LuceneField
	private String expertName;
	private int expertState;
	private String expertTel;
	@LuceneField
	private int id;
	private String password;
 
	private String username;

	public int getCategory() {
		return category;
	}

	public String getExpertEmail() {
		return expertEmail;
	}

	public String getExpertName() {
		return expertName;
	}

	public int getExpertState() {
		return expertState;
	}

	public String getExpertTel() {
		return expertTel;
	}

	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setExpertEmail(String expertEmail) {
		this.expertEmail = expertEmail;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public void setExpertState(int expertState) {
		this.expertState = expertState;
	}

	public void setExpertTel(String expertTel) {
		this.expertTel = expertTel;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}