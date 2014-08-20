/**
 * 
 */
package com.ucac.laihuiqiang.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Touris.service.impl.TouristServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.util.WriteLog;

@WebServlet("/manager/controlTouristStateServlet.do")
public class ControlTouristStateServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ControlTouristStateServlet() {
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
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int currentState;
		String success = null;
		
		currentState = Integer.parseInt(request.getParameter("currentState"));
		PrintWriter out = response.getWriter();
		
		try {
			success = TouristServiceImpl.getInstance().controlTouristState(currentState);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			WriteLog.writeLog("admin", "设置游客状态失败");
			response.sendRedirect("DBError.jsp");
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			WriteLog.writeLog("admin", "设置游客状态失败");
			response.sendRedirect("DBError.jsp");
		}
		
		if (!(success == null)) {
			WriteLog.writeLog("admin", "设置游客状态成功");
			out.print(success);
		}else{
			WriteLog.writeLog("admin", "设置游客状态失败");
			out.print("设置状态失败");
		}
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
