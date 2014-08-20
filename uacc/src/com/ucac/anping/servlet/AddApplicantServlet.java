package com.ucac.anping.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ucac.Applicant.service.ApplicantService;
import com.ucac.Applicant.service.impl.ApplicantServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
@WebServlet("/manager/addApplicant.do")
public class AddApplicantServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AddApplicantServlet() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	 
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  boolean  result=true;
		 boolean  isMultipart  = ServletFileUpload.isMultipartContent(request);
		 int b = 0;
		if(isMultipart==true){
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// Configure a repository (to ensure a secure temp location is used)
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// Parse the request
			try {
				List<FileItem> items = upload.parseRequest(request);
			    Iterator<FileItem> fileIterator =items.iterator();
			    while(fileIterator.hasNext()){
			    	FileItem item = fileIterator.next();
			    	if(item.getName().substring(item.getName().lastIndexOf('.'), item.getName().length()).equals(".xls")){
			    		InputStream  stream  = item.getInputStream();
			    		HSSFWorkbook  book  = new HSSFWorkbook(stream);
			    		ApplicantService  service  = ApplicantServiceImpl.getInstance();
			    		try {
						     b  =	service.addExperts(book);
					 
						} catch (ErrorException e) {
							result=false;
							 e.printStackTrace();
							 response.sendRedirect("./admin_error.jsp");
						} catch (DBException e) {
							result=false;
							 e.printStackTrace();
							 response.sendRedirect("./admin_error.jsp");
						}
			    	} else{
			    		  b=0;
			    		 break;
			    	}
			    }
			} catch (FileUploadException e) {
				 result=false;
				 response.sendRedirect("./admin_error.jsp");
			}
		}
		 
		  if(b==1 && result==true){
			response.sendRedirect("./admin_managerApply.jsp");
		 }else if(b==0) {
			 response.sendRedirect("./admin_error.jsp");
		 }
		 
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
