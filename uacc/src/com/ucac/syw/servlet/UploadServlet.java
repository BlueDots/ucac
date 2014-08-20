/**
* @package_name   com.ucac.syw.servlet
* @file_name UploadServlet.java
* @author songyouwei
* @date 2013-8-27
*/
package com.ucac.syw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.po.Applicant;
import com.ucac.po.Work;
import com.ucac.syw.util.UploadUtils;
import com.ucac.util.WriteLog;

/** 
 * @ClassName: UploadServlet
 * @Description: TODO
 */
@WebServlet("/upload.do")
public class UploadServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		 
		
		PrintWriter out = response.getWriter();
		
//		Applicant applicant = new Applicant();
//		applicant.setId(1);
		//check session
		Applicant applicant = (Applicant)request.getSession().getAttribute("applicant");
		if (applicant == null) {
			out.print("用户未登录！可能是浏览器兼容问题，请更换其他浏览器尝试！");
			out.flush();
			out.close();
			return;
		}

		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			out.print("不是多媒体内容！");
			out.flush();
			out.close();
			return;
		}
		
		// Create a factory for disk-based file items
		FileItemFactory factory = new DiskFileItemFactory();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// Parse the request
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			Iterator<FileItem> iter = fileItems.iterator();
			
			//check file count
			int fileCount = getFileCount(fileItems);
			if (fileCount <= 0) {
				out.print("没有上传文件！");
				out.flush();
				out.close();
				return;
			}
			
			while (iter.hasNext()) {
			    FileItem fileItem = iter.next();
			    System.out.println(fileItem.getFieldName()+"---------");
			    if(fileItem.getFieldName().equals("regForm")){
			    	fileItem.setFieldName("regform");
			    }
			    
			    //根据FileItem的size>0判断文件非空
			    if (!fileItem.isFormField() && fileItem.getSize()>0) {
			        //save the file
			    	UploadUtils.verifyAndSaveFile(fileItem, applicant.getId());
			    	//log
			    	WriteLog.writeLog("申报人员ID为"+applicant.getId(), "上传了"+fileItem.getFieldName());
			    }
			}
			
			//如果所有文件都已经传了，修改审核状态为-1
			if (UploadUtils.AllFilesUploaded(applicant.getId())) {
				Work work = new Work();
				work.setId(applicant.getId());
				work.setWorkState(-1);
				EntityDaoImpl.getInstance().update(work);
				//log
		    	WriteLog.writeLog("申报人员ID为"+applicant.getId(), "所有文件都已经传了，修改审核状态为-1");
			}
			
			//成功执行
			out.print("上传成功！");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.print(e.getMessage());
			out.flush();
			out.close();
		}
	}
	
	/**
	* @Author: songyouwei
	* @Title: getFileCount 
	* @Description 取得非空文件数目
	* @param fileItems
	* @return int
	 */
	private int getFileCount(List<FileItem> fileItems) {
		int count = 0;
		Iterator<FileItem> iter = fileItems.iterator();
		while (iter.hasNext()) {
			FileItem fileItem = iter.next();
			if (fileItem.getSize()>0) {
				count ++;
			}
		}
		return count;
	}

}
