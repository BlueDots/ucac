package com.ucac.chengzhengqing.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.syw.exception.UserException;
import com.ucac.user.service.impl.UserServiceImpl;
 
import com.ucac.util.JavaMailUtil;
import com.ucac.util.MD5Util;
import com.ucac.util.PropUtil;
import com.ucac.vo.Mail_SendProper;
import com.ucac.vo.PasswordFindingInfo;
@WebServlet("/findPassword.do")
public class FindPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 陈振清
	 * @todo    找回密码之验证邮箱
	 * @param request
	 * @param response
	 * @return	
	 * @returntype 
	 */
    
	public FindPasswordServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestUrl  = request.getRequestURL().toString();
	
	    requestUrl=	requestUrl.substring(0, requestUrl.lastIndexOf("/"));
	 
	    request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String msg = "邮件发送成功，请注意查收！";
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String verificationCode = request.getParameter("verificationCode");
		
		if (username == null || email == null || verificationCode == null) {
			return;
		}
		PasswordFindingInfo passwordFindingInfo = new PasswordFindingInfo();
		passwordFindingInfo.setUsername(username);
		passwordFindingInfo.setEmail(email);
		passwordFindingInfo.setVerificationCode(verificationCode);
		
		System.out.println("用户名："+username+"邮箱："+email+"验证码："+verificationCode);
		String captcha = (String)request.getSession().getAttribute("captcha");
		System.out.println("真正验证码："+captcha);
		try {
			UserServiceImpl.getInstance().getPassword(passwordFindingInfo,captcha);
		} catch (UserException | ErrorException | DBException e) {
			// TODO Auto-generated catch block
			out.println(e.getMessage());
			out.flush();
			out.close();
			return;
		}
		
		
		String usernameMd5 = null;
		
		if (username.length()>=4 && username.length()<=12) {
			usernameMd5 = MD5Util.EncoderByMd5(username+"Id");
		}
		final String CHECK_CODE = "checkCode";	
 
		String url = requestUrl+"/setPassword.jsp?username="   
        +username + "&" + CHECK_CODE + "=" + usernameMd5;
		 
		JavaMailUtil.setCheckUrlString(url);
		String[] toClientMails = {email}; 
		
		Mail_SendProper proper = new Mail_SendProper();
		proper.setSubject("江西省大学生精品活动展");
		proper.setContent("要使用新的密码, 请使用以下链接启用密码:<br/><a href="+url+">点击重新设置密码</a>");//最后一个参数是null时才需要设置
		JavaMailUtil.sendMultiMails(JavaMailUtil.defalutMailSendProper(proper),toClientMails ,null);
		//System.out.println(email+"mnmnmnmn");
		request.getServletContext().setAttribute("username", username);
		System.out.println("find的Application username："+username);
		out.println(msg);
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	

}
