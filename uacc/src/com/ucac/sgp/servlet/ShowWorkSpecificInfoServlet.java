package com.ucac.sgp.servlet;

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
import com.ucac.vo.WorkUrl;


@WebServlet("/ShowWorkSpecificInfo.do")
public class ShowWorkSpecificInfoServlet extends HttpServlet {
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
		String applicantIdString = request.getParameter("id");
		PrintWriter out = response.getWriter();
		//System.out.println(applicantIdString);
		WorkUrl wUrl = workService.findSpecificWorkById(applicantIdString);
		
		
		Gson gson = new Gson();
		String urlString = gson.toJson(wUrl);
		out.print(urlString);
		out.flush();
		out.close();
		
	}

}
