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
import com.ucac.syw.exception.UserException;
import com.ucac.user.service.impl.UserServiceImpl;
import com.ucac.util.MD5Util;
import com.ucac.util.WriteLog;

@WebServlet("/setPassword.do")
public class SetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 陈振清
	 * @todo    重设密码
	 * @param request
	 * @param response
	 * @return	
	 * @returntype 
	 */
    
	public SetPasswordServlet() {
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
		
		String username = request.getParameter("username");
		String checkCode = request.getParameter("checkCode");
		String newPassword = request.getParameter("password");
		String usernameinApplication = (String) request.getServletContext().getAttribute("username");
		
		if (username ==null || checkCode == null || newPassword == null || usernameinApplication == null) {
			out.print("出现操作错误！");
			out.flush();
			out.close();	
			return;
		}
		System.out.println("用户名："+username);
		System.out.println("md5："+checkCode);
		System.out.println("新密码："+newPassword);
		System.out.println("在application的用户名："+usernameinApplication);
		
		if (!MD5Util.EncoderByMd5(username+"Id").equals(checkCode)) {
			out.print("操作错误！");
			out.flush();
			out.close();
			return;
		}
			try {
				try {
					UserServiceImpl.getInstance().updatePassword(username, MD5Util.EncoderByMd5(newPassword));
					out.print("修改密码成功！");
					System.out.println("修改成功！！！");
					WriteLog.writeLog(username, username+"成功修改了密码");
				} catch (UserException e) {
					// TODO Auto-generated catch block
					out.println(e);
				}
				request.getServletContext().removeAttribute(username);
			} catch (ErrorException | DBException e) {
				// TODO Auto-generated catch block
				out.println("系统错误，请重试!");
			}
			
		
		out.flush();
		out.close();		
		//System.out.println(username);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	

}
