package com.ucac.chenjiang.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Score.service.ScoreService;
import com.ucac.Score.service.impl.ScoreServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Expert;
import com.ucac.po.ExpertAssess;
import com.ucac.util.WriteLog;


@WebServlet("/UpdateWork.do")
public class UpdateWorkServlet extends HttpServlet {
	
	private static ScoreService scoreService = ScoreServiceImpl.getInstance();
	
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
		//接受分数字符串
		String videoScoreString = request.getParameter("videoScore");
		String pictureScoreString = request.getParameter("pictureScore");
		String documentScoreString = request.getParameter("documentScore");
		String expertIdString = request.getParameter("expertId");
		String applicantIdString = request.getParameter("applicantId");
		String idString = request.getParameter("id");
		double videoScore = 0;
		double pictureScore = 0;
		double documentScore = 0;
		double score = 0;//总分
		int applicantId = 0;
		int expertId = 0;
		int id = 0;
		//转换
		try {
			videoScore = Double.parseDouble(videoScoreString);
			pictureScore = Double.parseDouble(pictureScoreString);
			documentScore = Double.parseDouble(documentScoreString);
			applicantId = Integer.parseInt(applicantIdString);
			expertId = Integer.parseInt(expertIdString);
			id = Integer.parseInt(idString);
		} catch (NumberFormatException e) {
			out.print("FormatError");
			return ;
		}
		
		if(videoScore>100.00||pictureScore>100.00||documentScore>100.00) {
			out.print("NumberError");
			return;
		}
		
		try {
			score = scoreService.calculateTotalScore(videoScore, documentScore, pictureScore);
		} catch (ErrorException e) {
			response.sendRedirect("error.jsp");
			e.printStackTrace();
			
		} catch (DBException e) {
			response.sendRedirect("error.jsp");
			e.printStackTrace();
		}
		
		//创建一个ExpertAssess对象
		  ScoreServiceImpl scoreServiceImpl = (ScoreServiceImpl)scoreService;
		  ExpertAssess expertAssess = scoreServiceImpl.createExpertAssess(videoScore, pictureScore, documentScore, score, expertId, applicantId);
		  expertAssess.setId(id);
		  System.out.println(expertAssess);	
		try {
			scoreService.updateExpertAssess(expertAssess );
			//日志
			Expert expert =  (Expert)request.getSession().getAttribute("expert");
			String expertName = expert.getExpertName();
			WriteLog.writeLog(expertName, "修改了applicantId为:"+applicantId+"的参赛者的分数");				
		} catch (DBException e) {
			response.sendRedirect("error.jsp");
			e.printStackTrace();
		} catch (ErrorException e) {
			response.sendRedirect("error.jsp");
			e.printStackTrace();
		}
		
		out.print("Success");
		
		
		out.flush();
		out.close();
		
		
	}
	
}
