package com.ucac.li.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Expert.service.impl.ExpertServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Expert;

@WebServlet("/searchExpertInfo.do")
public class SearchExpertInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public SearchExpertInfoServlet() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//接收参数
		String expertName = request.getParameter("expertName");
	
		//输入为空
		if(expertName==null){
			
			out.print("[]");
			
		}else{
					
				try {
					
					Expert expert = ExpertServiceImpl.getInstance().searchSingleExpert(expertName);
					
					//查询为空
					if(expert==null){
						
						out.print("[]");
					}else{
						
						StringBuffer strB = new StringBuffer("[");
						
						//专家编号id
						int expertId = expert.getId();
						
						//所在类别 
						int category = expert.getCategory();
						
						//专家姓名(直接传入)
						
						//专家状态
						int expertState = expert.getExpertState();
						
						
						
						
						strB.append("{\"expertId\":"+expertId+",\"category\":"+category+",\"expertName\":\""+expertName+"\",\"expertState\":"+expertState+"},");
					
						strB.deleteCharAt(strB.length()-1);
						strB.append("]");
						System.out.println(strB);
						out.print(strB);
					}
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
