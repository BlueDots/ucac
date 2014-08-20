package com.ucac.sgp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Work.service.WorkService;
import com.ucac.Work.service.impl.WorkServiceImpl;
import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Applicant;
import com.ucac.util.CalculateScore;
import com.ucac.util.JavaMailUtil;
import com.ucac.util.WriteLog;
import com.ucac.vo.Mail_SendProper;
@WebServlet("/manager/modifyAccountWorkState.do")
public class modifyWorkStateServlet extends HttpServlet{
	private WorkService workService = WorkServiceImpl.getInstance();
	private EntityDao entityDao = EntityDaoImpl.getInstance();
	List<Applicant> applicants = new ArrayList<Applicant>();
	
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
		List<Applicant> applicants = new ArrayList<Applicant>();//存储id
		String assessType = request.getParameter("assessType");//审核类型
		String workIdsString = "";//work id的字符串
		String reWorkids = request.getParameter("workIds");
		
		if(reWorkids == null) {
			out.print("Error");
			return ;
		}
		
		StringBuffer workidBuffer = new StringBuffer(reWorkids);
		//System.out.println(workidBuffer);
		workIdsString = workidBuffer.substring(0, workidBuffer.length()-1);
		//System.out.println(WorkIdsString);
		//System.out.println(assessType);
		String[] ids = workIdsString.split(",");//分解Id字符串
		int[] nid = new int[ids.length];
		try {
			for(int i=0;i<nid.length;i++){
				nid[i] = Integer.parseInt(ids[i]);
				//System.out.println(nid[i]);
			}
		} catch (NumberFormatException e) {
			out.print("Error");
			return ;
		}
		String[] emials = new String[ids.length];
		for(int i=0;i<nid.length;i++){
			Applicant applicant = null;
			try {
				applicant = entityDao.findById(Applicant.class, nid[i]);
			} catch (ErrorException e) {
				response.sendRedirect("./manager/admin_error.jsp"); 
				e.printStackTrace();
			} catch (DBException e) {
				response.sendRedirect("./manager/admin_error.jsp"); 
				e.printStackTrace();
			}
			applicants.add(applicant);
			emials[i] = applicant.getTeacherEmail();
		}
		
		
		
		int count = 0;
		try {
			count = workService.checkWorkContent(applicants, assessType);
			
			//日志
			
			if(assessType.equals("assess")) {
				WriteLog.writeLog("管理员", "通过了applicantId分别为:"+workIdsString+"的参赛人的作品");
			}else{
				WriteLog.writeLog("管理员", "没通过applicantId分别为:"+workIdsString+"的参赛人的作品");
			}			
		} catch (ErrorException e) {
			 response.sendRedirect("./manager/admin_error.jsp");  
			e.printStackTrace();
		} catch (DBException e) {
			 response.sendRedirect("./manager/admin_error.jsp");  
			e.printStackTrace();
		}
		
		System.out.println(count);
		
		out.print("success");
		//发送邮件
/*		for(int i=0;i<emials.length;i++)
			System.out.println(emials[i]);*/
		if(assessType.equals("notAssess")) {
			Mail_SendProper proper = new Mail_SendProper();
			JavaMailUtil.sendMultiMails(JavaMailUtil.defalutMailSendProper(proper),emials ,"ApplicantNotAssess.txt");
			System.out.println("success");
		}
		out.flush();
		out.close();

	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
}
