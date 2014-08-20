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
import com.ucac.user.service.impl.UserServiceImpl;
import com.ucac.util.WriteLog;

@WebServlet("/deleteApply.do")
public class DeleteApplyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 陈振清
	 * @todo    删除申报人员
	 * @param request
	 * @param response
	 * @return	
	 * @returntype 
	 */
    
	public DeleteApplyServlet() {
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
		
		String username = request.getParameter("applyName");
		System.out.println(username+"-----------");
		try {
			
			UserServiceImpl.getInstance().deleteApplicant(username);
			WriteLog.writeLog("管理员","成功删除申报人员"+username); 
			out.print("删除成功");
			
		} catch (ErrorException | DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		out.flush();
		out.close();
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	

}
