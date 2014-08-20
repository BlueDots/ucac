package com.ucac.anping.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ucac.Applicant.service.impl.ApplicantServiceImpl;
import com.ucac.Expert.service.impl.ExpertServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Admin;
import com.ucac.util.WriteLog;

@WebServlet("/manager/exportExcel.do")
public class ExportExcelServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ExportExcelServlet() {
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

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 * 
	 *             category 是类型 type 是需要导出的类型 比如有expert 和 result 和 expertAction
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String category = request.getParameter("category");
		String type = request.getParameter("type");

		if (type == null) {
			response.sendRedirect("./admin_error.jsp");
			return;
		}

		int cat = 0;  //這個用啦存類別
		try {
			cat = category == null ? 7 : Integer.parseInt(category);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
			request.setAttribute("errorMesg", e1.getMessage());
			request.getRequestDispatcher("./admin_error.jsp").forward(
					request, response);
			return;
		}

		

		if (type.equals("expert")) { // 导出的是专家帐号

			try {
				response.setHeader("Content-disposition", "attachment; filename=ucac_"
						+ type + ".xls");
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				OutputStream output = response.getOutputStream();
				output.flush();
				HSSFWorkbook book = null;
				book = ExpertServiceImpl.getInstance().exportHSSFbook(cat);
				 Admin  admin  = (Admin) request.getSession().getAttribute("admin");
				WriteLog.writeLog(admin.getUsername(), "导出专家帐号");
				
				book.write(output);
				output.close();
			} catch (ErrorException e) {
				e.printStackTrace();
				response.setContentType("html");
				request.setAttribute("errorMesg", e.getMessage());
				request.getRequestDispatcher("./admin_error.jsp")
						.forward(request, response);
				 return;
			} catch (DBException e) {
				e.printStackTrace();
				response.setContentType("html");
				request.setAttribute("errorMesg", e.getMessage());
				request.getRequestDispatcher("./admin_error.jsp")
						.forward(request, response);
				 return;
			}

		} else if (type.equals("result")) { // 导出的是比赛结果
			int sortType = Integer.parseInt(request.getParameter("sortType"));
		 
			
			try {
			response.setHeader("Content-disposition", "attachment; filename=ucac_"
					+ type + ".xls");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			OutputStream output = response.getOutputStream();
			output.flush();
			HSSFWorkbook book = null;
			
				book = ExpertServiceImpl.getInstance().exportExcelWorkResult(cat, sortType);
				 Admin  admin  = (Admin) request.getSession().getAttribute("admin");
				WriteLog.writeLog(admin.getUsername(), "导出比赛结果");
			book.write(output);
			output.close();
			}  catch (DBException e) {
			 	e.printStackTrace();
			 	response.setContentType("html");
				request.setAttribute("errorMesg", e.getMessage());
				request.getRequestDispatcher("./admin_error.jsp")
						.forward(request, response);
				 return;
			}
			
		} else if (type.equals("record")) { // 导出的是专家打分记录
			String expertId = request.getParameter("expertId");
			int expert = 0; // 准备专家帐号
			
			try {
				expert = expertId == null ? 7 : Integer.parseInt(expertId);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
				response.setContentType("html");
				request.setAttribute("errorMesg", e1.getMessage());
				request.getRequestDispatcher("./admin_error.jsp")
						.forward(request, response);
			  return;
			}
			
			
			try {
				response.setHeader("Content-disposition", "attachment; filename=ucac_"
						+ type + ".xls");
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				OutputStream output = response.getOutputStream();
				output.flush();
				HSSFWorkbook book = null;
				book = ExpertServiceImpl.getInstance().exportExpertRecord(expert, cat);
				 Admin  admin  = (Admin) request.getSession().getAttribute("admin");
				WriteLog.writeLog(admin.getUsername(), "导出专家打分记录");
				book.write(output);
				output.close();
			 }  catch (DBException e) {
				e.printStackTrace();
				response.setContentType("html");
				request.setAttribute("errorMesg", e.getMessage());
				request.getRequestDispatcher("./admin_error.jsp")
						.forward(request, response);
			 
				return;
			}
		 
         
		}else if(type.equals("apply")){
			try {
				response.setHeader("Content-disposition", "attachment; filename=ucac_"
						+ type + ".xls");
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				OutputStream output = response.getOutputStream();
				output.flush();
				
				HSSFWorkbook book = null;
			    book = ApplicantServiceImpl.getInstance().getExcelByApplicant();
			
				Admin  admin  = (Admin) request.getSession().getAttribute("admin");
				WriteLog.writeLog(admin.getUsername(), "导出申报人员名单记录");
				book.write(output);
				output.close();
			 }  catch (DBException e) {
				e.printStackTrace();
				response.setContentType("html");
				request.setAttribute("errorMesg", e.getMessage());
				request.getRequestDispatcher("./admin_error.jsp")
						.forward(request, response);
			 
				return;
			}	 catch (ErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setContentType("html");
				request.setAttribute("errorMesg", e.getMessage());
				request.getRequestDispatcher("./admin_error.jsp")
						.forward(request, response);
			}
			
		} else {
			response.sendRedirect("./admin_error.jsp");
			 
		}

		

		 
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
