/**
* @package_name   com.ucac.syw.servlet
* @file_name LogoutServlet.java
* @author songyouwei
* @date 2013-8-26
*/
package com.ucac.syw.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * @ClassName: LogoutServlet
 * @Description: TODO
 */
@WebServlet("/logout.do")
public class LogoutServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession().invalidate();
		//request.getSession().setAttribute("user", null);
		if(request.getParameter("value")!=null){
			response.sendRedirect("manager/login.jsp");
		}else{
		response.sendRedirect("login.jsp");
		}
	}

}
