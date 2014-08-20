package com.ucac.anping.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.po.Admin;

@WebServlet("/manager/checkAdminAgain.do")
public class CheckAdminAgainServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CheckAdminAgainServlet() {
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

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred 接收的参数： 收到的是管理员的用户名
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("json");
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		if (username== null) {
			out.print("{\"result\":0}");
		} else {
			Admin admin = (Admin) request.getSession().getAttribute("admin");
			if (admin == null) {
				out.print("{\"result\":0}");
			} else {
				// 开始判断管理员的密码
				String sessionusername = admin.getUsername();
			     
		 			if (username.equals(sessionusername)) {
						out.print("{\"result\":1}");
					
					} else {
						     //密码错误
						out.print("{\"result\":0}");
					}
		 	}
		}
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
