package com.ucac.chengzhengqing.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Admin;
import com.ucac.po.Applicant;
import com.ucac.po.Expert;
import com.ucac.syw.exception.UserException;
import com.ucac.user.service.impl.UserServiceImpl;
import com.ucac.util.MD5Util;
import com.ucac.vo.LoginInfo;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 陈振清
	 * 
	 * @todo 登录
	 * @param request
	 * @param response
	 * @return
	 * @returntype
	 */

	public LoginServlet() {
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
        
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String msg = "登录成功！";
		LoginInfo loginInfo = new LoginInfo(); // 用户登录的信息
		Object userobject = null; // 返回的用户对象
		
		
		String aboveString = request.getParameter("aboveboard");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (aboveString == null || username == null || password == null ) {
			return;
		}
		
		int aboveboard = Integer.parseInt(request.getParameter("aboveboard"));
		loginInfo.setAboveboard(aboveboard);
		loginInfo.setUsername(username);
		if (aboveboard == 1) {
			loginInfo.setPassword(MD5Util.EncoderByMd5(password));
		} else {
			loginInfo.setPassword(password);
		}
	
		String captcha = "back"; // 默认的session的验证码
		String verificationCode = "back"; // 默认的验证码
		if (aboveboard == 1) { // 前台有验证码
			loginInfo.setVerificationCode(request
					.getParameter("verificationCode")); // 前台用户输入的验证码
			captcha = (String) request.getSession().getAttribute("captcha");
		} else if (aboveboard == -1) { // 后台无验证码，自己默认正确
			loginInfo.setVerificationCode(verificationCode);
		}

		System.out.println(loginInfo.getAboveboard() + "前台");
		System.out.println(loginInfo.getUsername() + "用户名");
		System.out.println(loginInfo.getPassword() + "密码");
		System.out.println(loginInfo.getVerificationCode() + "验证码");
		System.out.println("真正验证码：" + captcha);
		
		
		try {
			userobject = UserServiceImpl.getInstance().checkUserInfo(loginInfo,
					captcha);
		} catch (UserException | ErrorException | DBException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			out.println(e.getMessage());
			out.flush();
			out.close();
			return;
		}

		int usernamelength = username.length();

		if (aboveboard == 1) { // 前台
			if (usernamelength >= 5 && usernamelength <= 12) { // the object is
																// an Applicant
				Applicant applicant = new Applicant();
				applicant = (Applicant) userobject;
				request.getSession().setAttribute("applicant", applicant);
				msg = "申报人员登录成功";
				System.out.println(applicant.getUsername() + "ok	");

			} else if (usernamelength == 4) { // the object is an Expert
				Expert expert = new Expert();
				expert = (Expert) userobject;
				request.getSession().setAttribute("expert", expert);
				msg = "专家登录成功";
			} else {
				out.print("用户名错误！");
				out.flush();
				out.close();
				return;
			}
		} 
		if (aboveboard == -1) { // 后台管理员页面
			System.out.println("11111111111");
			Admin admin = new Admin();
			admin = (Admin) userobject;
			request.getSession().setAttribute("admin", admin);
			msg = "管理员登录成功";

		}
		out.print(msg);
		out.flush();
		out.close();

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */

}
