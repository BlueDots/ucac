package com.ucac.syw.exception;

public class UserException extends Exception {
	
	public static String errorMsg_verificationCode = "验证码错误！";
	public static String errorMsg_username = "用户名不合法！需由6-10位字符的字母、数字、下划线组成，必须以字母开头！";
	public static String errorMsg_password = "密码不合法！需由6-12位字母+数字组成，必须以字母开头！";
	public static String errorMsg_password2 = "两次密码不一致！";
	public static String errorMsg_school = "学校不能为空！";
	public static String errorMsg_communityName = "社团名不能为空！";
	public static String errorMsg_phone = "电话不合法！";
	public static String errorMsg_email = "邮箱不合法！";
	public static String errorMsg_usernameorPassword = "您输入的帐号或密码有误！";

	public static String errorMsg_username_exists = "用户名已存在！";
	public static String errorMsg_communityName_exists = "社团名已存在！";
	
	
	//czq
	public static String errorMsg_emailFalse = "邮箱错误！";  
	public static String errorMsg_usernameFalse = "用户名不存在！";
	public static String errorMsg_expertNotFree = "您的号被封，请与管理员联系！";
    public static String error_diedStatus="您的帐号尚未激活,请先激活"; 
	public UserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}