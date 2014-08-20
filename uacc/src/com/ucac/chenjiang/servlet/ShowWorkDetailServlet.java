package com.ucac.chenjiang.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Expert.service.ExpertService;
import com.ucac.Expert.service.impl.ExpertServiceImpl;
import com.ucac.Work.service.WorkService;
import com.ucac.Work.service.impl.WorkServiceImpl;
import com.ucac.vo.WorkUrl;


@WebServlet("/ShowWorkDetail.do")
public class ShowWorkDetailServlet extends HttpServlet {
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
		String videoString = request.getParameter("videoScore");
		PrintWriter out = response.getWriter();
		System.out.println(applicantIdString);
		System.out.println(videoString);
		int applicantId = 0; //申报人ID
		try {
			applicantId = Integer.parseInt(applicantIdString);
		}catch (NumberFormatException e) {
			out.println("数据转换错误");
			return ;
		}
		WorkUrl wUrl = workService.findSpecificWorkById(applicantIdString);

		request.setAttribute("WorkUrl", wUrl);
		request.getRequestDispatcher("detail.jsp").forward(request, response);
		
		out.flush();
		out.close();
		
	}

}
