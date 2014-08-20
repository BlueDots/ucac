package com.ucac.anping.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Expert.service.ExpertService;
import com.ucac.Expert.service.impl.ExpertServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Admin;
import com.ucac.po.Expert;
import com.ucac.util.WriteLog;
import com.ucac.vo.Page;
import com.ucac.vo.QueryResult;
@WebServlet("/manager/expertsAction.do")
public class ExpertsActoinServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ExpertsActoinServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
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
	 * 无id的话就是添加,有id则是 更新
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
          if(request.getParameterValues("id")==null){
        	  /**添加部分****/
        		 if(request.getParameterValues("username")==null || request.getParameterValues("category")==null ||
        				 request.getParameterValues("expertName")==null || request.getParameterValues("expertTel")==null||
        						 request.getParameterValues("expertEmail")==null
        				 ){
        			 response.sendRedirect("./admin_error.jsp");
        			 return;
        		 }else{
                  String[] usernames   =  request.getParameterValues("username");
                  String[] categorys   =  request.getParameterValues("category");
                  String[] expertNames =  request.getParameterValues("expertName");
                  String[] expertTel   =  request.getParameterValues("expertTel");
                  String[] expertEmail =  request.getParameterValues("expertEmail");
                  List<Expert> experts  = new ArrayList<Expert>();
                  for(int i =0 ;i<usernames.length;i++){
                	  Expert expert  = new Expert();
                	  expert.setUsername(usernames[i]);
                	  expert.setCategory(Integer.parseInt(categorys[i]));
                	  expert.setExpertName(expertNames[i]);
                	  expert.setExpertTel(expertTel[i]);
                	  expert.setExpertEmail(expertEmail[i]);
                	  expert.setExpertState(1);
                	  experts.add(expert);
                  }
                  ExpertService  service  = ExpertServiceImpl.getInstance();
                  try {
        		    QueryResult<Expert> allexperts= service.addExpertInfo(experts);
        		    //添加日志
        		    Admin  admin  = (Admin) request.getSession().getAttribute("admin");
        		    WriteLog.writeLog(admin.getUsername(), "添加专家帐号");
        		    Page page  = new Page();
        		    page.setPage(1);
        		    page.setCount((int)allexperts.getTotalCount());
        		    request.setAttribute("category", 7);
        		    request.setAttribute("experts", allexperts);
        		    request.setAttribute("page", page);
        		    request.getRequestDispatcher("./admin_ExpertInfo.jsp").forward(request, response);
                  } catch (ErrorException e) {
        			 e.printStackTrace();
        			  request.setAttribute("errorMesg",e.getMessage());
        	          request.getRequestDispatcher("./admin_error.jsp").forward(request, response);
        	          return ;
        		  } catch (DBException e) {
        			 e.printStackTrace();
        			 request.setAttribute("errorMesg",e.getMessage());
        	          request.getRequestDispatcher("./admin_error.jsp").forward(request, response);
        	          return ;
        		  }
                  }
          }else{
        	/**更新部分**/   
        	  System.out.println("正在更新");
        	  String[] ids =  request.getParameterValues("id");
             /***全部更新吧*****/  
              List<Expert> experts  = new ArrayList<Expert>();
        	  for(int i=0 ;i<ids.length;i++){
        		  String username  = request.getParameter("username"+ids[i]);
                    if(username!=null){
                    	  //开始组件对象
                    	System.out.println(request.getParameter("category"+ids[i])+"----------category-----------");
                    	  Expert expert  = new Expert();
                    	  expert.setId(Integer.parseInt(ids[i]));
                    	  expert.setUsername(username);
                          expert.setExpertState(1);
                           if(request.getParameter("category"+ids[i])!=null)
                    		  expert.setCategory(Integer.parseInt(request.getParameter("category"+ids[i])));
                    	  if(request.getParameter("expertName"+ids[i])!=null)
                    		  expert.setExpertName(request.getParameter("expertName"+ids[i]));
                    	  if(request.getParameter("expertTel"+ids[i])!=null)
                    		  expert.setExpertTel(request.getParameter("expertTel"+ids[i]));
                          if(request.getParameter("expertEmail"+ids[i])!=null)
                        	  expert.setExpertEmail(request.getParameter("expertEmail"+ids[i]));
                           experts.add(expert);
                       
                          
                      } 
               }
            
        	  if(experts.size()!=0){
        		  ExpertService  service  = ExpertServiceImpl.getInstance();
            	try {
					service.modifyExpertInfo(experts);
					Admin  admin  = (Admin) request.getSession().getAttribute("admin");
	        	    WriteLog.writeLog(admin.getUsername(), "修改专家帐号");
				} catch (ErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("errorMesg",e.getMessage());
      	          request.getRequestDispatcher("./admin_error.jsp").forward(request, response);
      	          return ;
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("errorMesg",e.getMessage());
      	          request.getRequestDispatcher("./admin_error.jsp").forward(request, response);
      	          return ;
				}  
        	  } 
        	   
        	  request.getRequestDispatcher("./admin_ExpertInfo.jsp").forward(request, response);
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
