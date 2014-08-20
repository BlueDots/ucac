package com.ucac.laihuiqiang.servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Settings;
@WebServlet("/ControlTouristButtonServlet.do")
public class ControlTouristButtonServlet extends HttpServlet {

	
	/**
	 * Constructor of the object.
	 */
	public ControlTouristButtonServlet() {
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
        EntityDao entity = EntityDaoImpl.getInstance();
  
	  	int touristState = 0;
			try {
				touristState = entity.findById(Settings.class, 1).getTouristState();
			} catch (ErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
			request.setAttribute("touristState",touristState);
			String url = request.getParameter("url");
			if(url!=null){	
			  	request.getRequestDispatcher("./personal.jsp").forward(request, response);
				
			}else{
			request.getRequestDispatcher("./login.jsp").forward(request, response);
			}
	}

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
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
