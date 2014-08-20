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
import com.ucac.lhq.exception.FileConsistencyException;
import com.ucac.lhq.exception.FileNameIllegalException;

@WebServlet("/manager/restoreServlet.do")
public class RestoreServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public RestoreServlet() {
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

		int value;
		int flag = 0;
		String fileName = null;
		
		PrintWriter out = response.getWriter();
		
		fileName = request.getParameter("fileName");
		value = Integer.parseInt(request.getParameter("value"));
		
		try {
			flag = AdminServiceImpl.getInstance().restore(value, fileName);
		} catch (com.ucac.exception.IOException e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
		} catch (FileConsistencyException e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
		}catch (FileNameIllegalException e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
		} 

		if (flag == 1) {
			out.write("恢复成功!");
		}else{
			out.print("恢复失败!");
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
