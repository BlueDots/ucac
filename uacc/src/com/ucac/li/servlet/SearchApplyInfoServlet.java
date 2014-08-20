package com.ucac.li.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Applicant.service.impl.ApplicantServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Applicant;

@WebServlet("/searchApplyInfo.do")
public class SearchApplyInfoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SearchApplyInfoServlet() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//接收URL参数
		String applyName = request.getParameter("applyName");
		
		//判断输入为空
		if(applyName==""){
			out.print("[]");
			
		}else{
			try {
				
				Applicant applicant = ApplicantServiceImpl.getInstance().showApplyInfo(applyName);
				
				//判断是否搜索到结果
				if(applicant==null){
					out.print("[]");
				}else{
					
					StringBuffer strB = new StringBuffer("[");
					
					//用户名直接传入
					
					String school = applicant.getSchool();
					
					String communityName = applicant.getCommunityName();
					
					String teacherName = applicant.getTeacherName();
					
					String teacherPhone = applicant.getTeacherPhone();
					
					String teacherEmail = applicant.getTeacherEmail();
					
					strB.append("{\"applyName\":\""+applyName+"\",\"school\":\""+school+"\",\"communityName\":\""+communityName+"\",\"teacherName\":\""+teacherName+"\",\"teacherPhone\":\""+teacherPhone+"\",\"teacherEmail\":\""+teacherEmail+"\"},");
				
					strB.deleteCharAt(strB.length()-1);
					strB.append("]");
					
					out.print(strB);
				}
				
				//System.out.println(applicant.getCommunityName());
				
			} catch (ErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
