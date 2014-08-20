package com.ucac.chenjiang.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ucac.Expert.service.ExpertService;
import com.ucac.Expert.service.impl.ExpertServiceImpl;
import com.ucac.Work.service.WorkService;
import com.ucac.Work.service.impl.WorkServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.ExpertAssess;
import com.ucac.vo.WorkUrl;


@WebServlet("/ShowWork.do")
public class ShowWorkServlet extends HttpServlet {
	private static ExpertService expertService = ExpertServiceImpl.getInstance();
	private static WorkService workService = WorkServiceImpl.getInstance();
	
	/**
	 * 
	 * chengjiang
	 * @todo 显示打分界面
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @returntype void
	 */
	
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
		String applicantIdString = request.getParameter("applicantId");
		String expertIdString = request.getParameter("expertId");
		PrintWriter out = response.getWriter();
		//System.out.println(applicantIdString);
		//System.out.println(expertIdString);
		int applicantId = 0; //申报人ID
		int expertId = 0;//专家ID
		try {
			applicantId = Integer.parseInt(applicantIdString);
			expertId = Integer.parseInt(expertIdString);
		}catch (NumberFormatException e) {
			out.println("数据转换错误");
			return ;
		}
		ExpertAssess expertAssess = null;
		WorkUrl wUrl = workService.findSpecificWorkById(applicantIdString);
		try {
			 expertAssess= expertService.getExpertAssess(applicantId, expertId);
		} catch (ErrorException e) {
			response.sendRedirect("error.jsp");
			e.printStackTrace();
		} catch (DBException e) {
			response.sendRedirect("error.jsp");
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		String urlString = gson.toJson(wUrl);
		//System.out.println(urlString);
		if(expertAssess == null) {
			if(urlString != null) {
 			expertAssess = new ExpertAssess();
//				String expertAssessString = gson.toJson(expertAssess);
//				String totalString = "["+urlString+","+expertAssessString+"]";
//				//System.out.println(totalString);
//				
//				out.println(totalString);
				   StringBuffer   buffer = new StringBuffer("[");
		              
	    		   buffer.append(gson.toJson(wUrl));
	    		  buffer.append(",").append(gson.toJson(expertAssess));
	    		   buffer.append("]");
	    		   System.out.println(buffer.toString());
				 out.println(buffer.toString());
			
			}
		}
		else {
			if(urlString != null) {
				//System.out.println(expertAssess);
//				String expertAssessString = gson.toJson(expertAssess);
//				String totalString = "["+urlString+","+expertAssessString+"]";
				//System.out.println(totalString);
				
				   StringBuffer   buffer = new StringBuffer("[");
		              
	    		   buffer.append(gson.toJson(wUrl));
	    		  buffer.append(",").append(gson.toJson(expertAssess));
	    		   buffer.append("]");
	    		   System.out.println(buffer.toString());
				 out.println(buffer.toString());
			
			
			}
		}
		out.flush();
		out.close();
		
	}

}
