/**
* @package_name   com.ucac.syw.servlet
* @file_name GetUploadStateServlet.java
* @author songyouwei
* @date 2013-9-3
*/
package com.ucac.syw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.po.Applicant;
import com.ucac.syw.util.UploadUtils;

/** 
 * @ClassName: GetUploadStateServlet
 * @Description: TODO
 */
@WebServlet("/getUploadState.do")
public class GetUploadStateServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("json");  
		System.out.println("-----getUploadState-----"+request.getParameter("fieldName"));
		//check session
		Applicant applicant  = (Applicant)request.getSession().getAttribute("applicant");
		if (applicant == null) {
			return;
		}
		
		//check fieldName
		String fieldName = request.getParameter("fieldName");
		if (fieldName == null) {
			return;
		}

		PrintWriter out = response.getWriter();
		boolean ifExists = UploadUtils.getFileUploadStateByFieldName(applicant.getId(), fieldName);
		System.out.println(fieldName+"==========="+ifExists);
		if (ifExists) {
			out.print("{\"value\":\"<font color='green'>已上传！可选择文件重传！</font>\"}");
		} else {
			out.print("{\"value\":\"<font color='red'>还未上传！</font>\"}");
		}
		
		out.flush();
		out.close();
	}

}
