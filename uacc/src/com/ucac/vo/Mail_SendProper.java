package com.ucac.vo;

import java.util.Properties;


/**
 * 
 * @author Administrator
 *
 */
public class Mail_SendProper {

	private String Host;
	private String Port = "25";        //发送邮件的端口;
	private String SendAttress;        //邮件发送者的地址
	private String ReceiveAttress;	   //邮件接受者的地址
	
	private String userName;		   //
	private String password;
	private boolean isvalidate = true;
	
	private String subject;
	private String content;
	private String[] attachFileName;
	
	public Properties getProperties(){
		Properties p = new Properties();
		p.put("mail.smtp.host",this.Host);
		p.put("mail.smtp.port",this.Port);
		p.put("mail.smtp.auth",isvalidate?"true":"false");
		return p;
	}
	public String getHost() {
		return Host;
	}
	public void setHost(String host) {
		Host = host;
	}
	public String getPort() {
		return Port;
	}
	public void setPort(String port) {
		Port = port;
	}
	public String getSendAttress() {
		return SendAttress;
	}
	public void setSendAttress(String sendAttress) {
		SendAttress = sendAttress;
	}
	public String getReceiveAttress() {
		return ReceiveAttress;
	}
	public void setReceiveAttress(String receiveAttress) {
		ReceiveAttress = receiveAttress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isIsvalidate() {
		return isvalidate;
	}
	public void setIsvalidate(boolean isvalidate) {
		this.isvalidate = isvalidate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
