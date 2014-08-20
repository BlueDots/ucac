package com.ucac.chenjiang.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ucac.Expert.service.ExpertService;
import com.ucac.Expert.service.impl.ExpertServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.vo.WorkResult;
@WebServlet("/ShowExpertRecord.do")
public class ShowExpertRecordServlet extends HttpServlet {
	
	private static ExpertService expertService = ExpertServiceImpl.getInstance();
	/**
	 * 
	 * chengjiang
	 * @todo 显示单个专家打分记录【专家，管理员】
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
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
		PrintWriter out = response.getWriter();
		
		String expertIdString = request.getParameter("expertId");//拿到专家的id
		String categoryString = request.getParameter("category");//拿到专家的类别
		String nowpageString = request.getParameter("nowpage");//拿到当前页数
		String pageSizeString = request.getParameter("pageSize");//拿到当前页面显示记录条数
		String isFrontString = request.getParameter("isFront");//是否是前台
		System.out.println(expertIdString);
		System.out.println(categoryString);
		System.out.println(isFrontString);
		
		
		int expertId = 0;
		int category = 0;
		int nowpage = 0;
		int pageSize = 0;
		int total = 0;//总记录数
		int finished = 0;//评审完的个数
		boolean isFront = false;
		try{
		expertId = Integer.parseInt(expertIdString);
		category = Integer.parseInt(categoryString);
		nowpage = Integer.parseInt(nowpageString);
		pageSize = Integer.parseInt(pageSizeString);
		isFront = Boolean.parseBoolean(isFrontString);
		}catch (NumberFormatException e) {
			out.println("Error");
			return ;
		}
		try {
			List<WorkResult> workResults = expertService.getWorksInfo(expertId, category, nowpage, pageSize, isFront);
			Gson g = new Gson();
			String json = "";
			if(workResults.size()>0) {
				json = g.toJson(workResults);
				String resultString = expertService.numofWorkResult(expertId, category, isFront);
				String[] re = resultString.split(",");	
				total = Integer.parseInt(re[0]);
				finished = Integer.parseInt(re[1]);
				
				StringBuffer jsonBuffer = new StringBuffer(json);
				json = jsonBuffer.substring(0, jsonBuffer.length()-1);
				//加一个表示当前页码，总记录条数，页面显示记录条数的JSON对象
				json+=",{\"nowpage\":"+nowpage+",\"pageSize\":"+pageSize+",\"total\":"+total+",\"finished\":"+finished+"}]";
			}
			else {
				json = "[{\"nowpage\":"+nowpage+",\"pageSize\":"+pageSize+",\"total\":"+total+",\"finished\":"+finished+"}]";
			}
			System.out.println(json);
			out.println(json);
			
			out.flush();
			out.close();
			
		} catch (DBException e) {
			response.sendRedirect("error.jsp");
			e.printStackTrace();
		}
	}
}
