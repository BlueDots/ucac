/**
 * 
 */
package com.ucac.laihuiqiang.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Admin.service.impl.AdminServiceImpl;
import com.ucac.lhq.exception.FileNotExistException;
import com.ucac.lhq.exception.PathIllegalException;

@WebServlet("/manager/backupServlet.do")
public class BackUpServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BackUpServlet() {
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

		String path;
		int value;
		int flag = 0;
		
		value= Integer.parseInt(request.getParameter("value"));
		System.out.print(value);
		path = request.getParameter("path");
		System.out.print(path);
		PrintWriter out = response.getWriter();
		
		try {
			flag = AdminServiceImpl.getInstance().backup(value, path);
		} catch (PathIllegalException e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
		} catch (FileNotExistException e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
		} catch (com.ucac.exception.IOException e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
		}
		
		if (flag == 1) {
			out.write("备份成功!");
		}else{
			out.write("备份失败!");
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
