package com.ucac.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * 使用Gmail发送邮件
 * 
 * @author laihuiqiang
 */
public class JavaMail {
	
	private static Properties props = null;
	private static Session session = null;
	private static Message msg = null;
	private static String checkUrlString = null;
    /**
     * 
     * laihuiqiang
     * @todo 设置Property的属性
     * @param mailType 使用的邮件协议
     * @param mailPort 发送邮件对应的端口
     * @returntype void 
     */
	private static void setProperties(String mailType,String mailPort){	
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		props = System.getProperties();
		props.setProperty("mail.smtp.host", mailType);
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", mailPort);
		props.setProperty("mail.smtp.socketFactory.port",mailPort);
		props.put("mail.smtp.auth", "true");		
	}
	
	public static String getCheckUrlString() {
		return checkUrlString;
	}
	
	public static void setCheckUrlString(String checkUrlString) {
		JavaMail.checkUrlString = checkUrlString;
	}
	
	/**
	 * 
	 * laihuiqiang
	 * @todo 获取发送邮件的session实例
	 * @param userName 发送人的邮件名
	 * @param password 邮件密码
	 * @returntype void
	 */
	private static void getSessionInstance(final String userName,final String password){	
		session = Session.getDefaultInstance(props,
				new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userName, password);
					}
				});
	}
	/**
	 * 
	 * chenzhenqing   不可删      
	 * @todo 设置普通的邮件信息
	 * @param toMail 接收人的邮件
	 * @param userName 发送人的邮件名
	 * @throws AddressException 发送的地址异常
	 * @throws MessagingException 信息异常
	 * @returntype void
	 */
	public static Boolean setMessage(String toMail,String userName){
		msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(userName));
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toMail, false));
			msg.setSubject("江西省大学生精品活动展");  
			
			msg.setContent("要使用新的密码, 请使用以下链接启用密码:<br/><a href="+getCheckUrlString()+">点击重新设置密码</a>","text/html;charset=utf-8");  
			//msg.setText("要使用新的密码, 请使用以下链接启用密码:<br/><a href='hhh'>点击重新设置密码</a>","text/html;charset=utf-8");
			msg.setSentDate(new Date());
			checkUrlString = "http://localhost:8888/UaccTest/findPassword.jsp";
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo 设置html格式的邮件信息
	 * @param toMail 接收人的邮件
	 * @param userName 发送人的邮件名
	 * @param content 邮件的内容
	 * @throws AddressException 发送的地址异常
	 * @throws MessagingException 信息异常
	 * @returntype void
	 */
	public static Boolean setHtmlMessage(String toMail,String userName,String content){
		
		Multipart mp = new MimeMultipart();
        MimeBodyPart mbp = new MimeBodyPart();   
		try {
			mbp.setContent(content,"text/html;charset=GB2312");  // 设定邮件内容的类型为 text/plain 或 text/html   
	        mp.addBodyPart(mbp);  
	        msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(userName));
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toMail, false));
			msg.setSubject("江西省大学生精品活动展");
			msg.setContent(mp); 
			msg.setSentDate(new Date());
			msg.setText("你好");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo 通过读html模板获取邮件内容
	 * @param htmlPath html模板的路径
	 * @return 
	 * @throws IOException 读写异常
	 * @returntype String 返回的邮件内容
	 */
	private static String getMailContent(String htmlPath){
		String content = "";
		String temp = "";
		BufferedReader br = null;
		InputStreamReader fr;
		try {
			fr = new InputStreamReader(new FileInputStream(htmlPath),"gb2312");
			 br = new BufferedReader(fr);
		        while ((temp=br.readLine()) != null){
		        	content = content + temp;
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "文件读取异常";
		}
		return content;
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo 
	 * @param mailType 使用的邮件协议
	 * @param mailPort 发送邮件对应的端口
	 * @param userName 发送人的邮件名
	 * @param password 邮件密码
	 * @param toMail 接收人的邮件
	 * @param htmlPath html模板的路径
	 * @throws MessagingException 信息异常
	 * @throws IOException 读写异常
	 * @returntype void
	 */
	private static Boolean sendSingleMail(String mailType,String mailPort,String userName,String password,String toMail,String htmlPath) throws MessagingException, IOException{	
		
		setProperties(mailType,mailPort);//设置邮件类型和邮件端口
		getSessionInstance(userName,password);//获取邮件session实例
		if (htmlPath == null) {
			
		setMessage(toMail,userName);//设置邮件的内容
		}else {
			setHtmlMessage(toMail,userName,getMailContent(htmlPath));			
		}
		Transport.send(msg);//发送邮件消息
		return true;
		
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo 
	 * @param mailType 使用的邮件协议
	 * @param mailPort 发送邮件对应的端口
	 * @param userName 发送人的邮件名
	 * @param password 邮件密码
	 * @param toMail 接收人的邮件
	 * @param htmlPath html模板的路径
	 * @throws MessagingException 信息异常
	 * @throws IOException 读写异常
	 * @returntype void
	 */
	public static String sendMultiMails(String mailType,String mailPort,String userName,
			String password,String[] toClientMails,String htmlPath){

		String message = "邮件全部发送成功!";
		for (int i = 0; i < toClientMails.length; i++) {
			try {
				sendSingleMail(mailType,mailPort,userName,password,toClientMails[i],htmlPath);
			} catch (MessagingException | IOException e) {
				// TODO Auto-generated catch block
				message = "发送失败!已发送" + i + "封,未发送" +  (toClientMails.length - i) + "封";
			}
		}
		return message;
	}
	
	
	
	
	
	
	
}