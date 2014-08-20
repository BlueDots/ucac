package com.ucac.vo;
/**
 * 【专家、申报人员】登录验证使用的VO
 * @author Administrator
 *
 */
public class LoginInfo {
	private String password;
	private String username;
	private String verificationCode;
	private int aboveboard;
	public int getAboveboard() {
		return aboveboard;
	}	
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public void setAboveboard(int aboveboard) {
		this.aboveboard = aboveboard;
	}
}	
