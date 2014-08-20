package com.ucac.util;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.ucac.vo.MailAttorney;
import com.ucac.vo.Mail_SendProper;

public class JavaMailUtil {
	/**
	 * 
	 * @param mailSender
	 * @return
	 */
	private static String checkUrlString = null;
	public static boolean sendTextMail(Mail_SendProper mailSender){
		MailAttorney attorney = null;
		if (mailSender.isIsvalidate()) {
			attorney = new MailAttorney(mailSender.getUserName(), mailSender.getPassword());
		}
		Session sendMailSession = Session.getInstance(mailSender.getProperties(),attorney);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(mailSender.getSendAttress());
			mailMessage.setFrom(from);
			Address to = new InternetAddress(mailSender.getReceiveAttress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			mailMessage.setSubject(mailSender.getSubject());
			
			mailMessage.setSentDate(new Date());
			String mailContent = mailSender.getContent();
			
			mailMessage.setText(mailContent);
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException e) {
			// TODO: handle exception
			System.out.print("邮件发送失败!");
		}
		return false;
	}
	
	public static boolean sendHtmlMail(Mail_SendProper mailSender){
		MailAttorney attorney = null;
		if (mailSender.isIsvalidate()) {
			attorney = new MailAttorney(mailSender.getUserName(), mailSender.getPassword());
		}
		Session sendMailSession = Session.getInstance(mailSender.getProperties(),attorney);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(mailSender.getSendAttress());
			mailMessage.setFrom(from);
			Address to = new InternetAddress(mailSender.getReceiveAttress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			mailMessage.setSubject(mailSender.getSubject());
			mailMessage.setSentDate(new Date());
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(mailSender.getContent(),"text/html;charset=UTF-8");
			
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException e) {
			// TODO: handle exception
			System.out.print("邮件发送失败!");
		}
		return false;
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
	private static String getMailContent(String htmlPath) throws IOException{
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
			throw e;
		}
		return content;
	}
	
	public static Mail_SendProper defalutMailSendProper(Mail_SendProper proper){
		
		proper.setHost("smtp.qq.com");
		proper.setPort("25");
		proper.setContent("要使用新的密码, 请使用以下链接启用密码:<br/><a href="+checkUrlString+">点击重新设置密码</a>");
		proper.setIsvalidate(true);
		proper.setUserName("346423152");
		proper.setPassword("ruanjian11");
		proper.setSendAttress("346423152@qq.com");
		proper.setReceiveAttress("laihuiqiang11@gmail.com");
		proper.setSubject("江西农业大学社团精品活动展");
		
		return proper ;
	}
	
	private static void sendSingleMail(Mail_SendProper proper,String toMail,String htmlPath) throws MessagingException, IOException{	
		//setProperties(mailType,mailPort);//设置邮件类型和邮件端口
		//getSessionInstance(userName,password);//获取邮件session实例
		if (htmlPath == null) {
			sendHtmlMail(proper);//设置邮件的内容
		}else{
			htmlPath = JavaMailUtil.class.getClassLoader().getResource("ApplicantNotAssess.txt").getPath();
			System.out.print(htmlPath);
			proper.setContent(getMailContent(htmlPath));
			sendHtmlMail(proper);
		}
		//Transport.send(msg);//发送邮件消息
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
	public static String sendMultiMails(Mail_SendProper proper,String[] toClientMails,String htmlPath){

		String message = "邮件全部发送成功!";
		for (int i = 0; i < toClientMails.length; i++) {
			try {
				proper.setReceiveAttress(toClientMails[i]);
				sendSingleMail(proper,toClientMails[i],htmlPath);
			} catch (MessagingException | IOException e) {
				// TODO Auto-generated catch block
				message = "发送失败!已发送" + i + "封,未发送" +  (toClientMails.length - i) + "封";
				return message;
			}
		}
		System.out.print(message);
		return message;
	}
	public static String getCheckUrlString() {
		return checkUrlString;
	}
	
	public static void setCheckUrlString(String checkUrlString) {
		JavaMailUtil.checkUrlString = checkUrlString;
	}
	
	public static void main(String[] args) throws MessagingException {
		String[] toClientMails ={"laihuiqiang11@gmail.com"};//发送人的邮件
		Mail_SendProper proper = new Mail_SendProper();
		//proper.setContent("this is my new javaMail");//最后一个参数是null时才需要设置
		JavaMailUtil.sendMultiMails(defalutMailSendProper(proper),toClientMails ,null);
		//发送邮件的方法，最后一个参数是null表示不是从文件中读取，自己设置内容
	}
}
