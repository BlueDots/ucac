package com.ucac.sgp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Expert.service.ExpertService;
import com.ucac.Expert.service.impl.ExpertServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Expert;
import com.ucac.util.WriteLog;

@WebServlet("/manager/modifyAccountState.do")
public class modifyAccountStateServlet extends HttpServlet{
	private ExpertService expertService = ExpertServiceImpl.getInstance(); 
	/**
	 * 宋国平
	 * TODO
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	/**
	 * 宋国平
	 * TODO
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String expertIdString = request.getParameter("expertId");
		System.out.println(expertIdString);
		
		int expertId = 0;
		try {
			expertId = Integer.parseInt(expertIdString);
		}catch (NumberFormatException e) {
			out.print("Error");
			return ;
		}
		Expert expert = new Expert();
		expert.setId(expertId);
		System.out.println(expertId);
		try {
			expertService.modifyAccountState(expert);
			//日志
			WriteLog.writeLog("管理员", "修改了expertId为:"+expertId+"的专家的状态");			
		} catch (ErrorException e) {
			response.sendRedirect("./manager/admin_error.jsp");  
			e.printStackTrace();
		} catch (DBException e) {
			response.sendRedirect("./manager/admin_error.jsp");  
			e.printStackTrace();
		}
		out.print("success");

		out.flush();
		out.close();		
	}

	
	 
}
