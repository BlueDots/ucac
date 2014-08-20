package com.ucac.anping.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ucac.Expert.service.impl.ExpertServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Expert;
import com.ucac.vo.Page;
import com.ucac.vo.QueryResult;

@WebServlet("/manager/showExperts.do")
public class ShowExpertsServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ShowExpertsServlet() {
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
	 * 通过分页,类型来拿到你想要拿到的数据.默认进入的是显示全部的和第一页的
	 * 这里我们需要表示到底是什么样的一个请求啊，如果是需要刷新的,那我们就是一刷新的,如果是AJAX的，那返回的也应该是AJAX的啊
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 * 
	 * 需要接收的参数有 requestUrl  用来判断是跳会到哪里去,不传的话默认是回到admin_viewResult.html
	 *  requestType  是返回的格式,是以attribute的形式返回还是ajax
	 *  category 如果不传默认是7 就是全部
	 *  page 如果不传默认是第一页
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	 
	   String  requestUrl  =request.getParameter("requestUrl");    
	  
	   int category = 0 ;
    
       Page page  = new Page();
    
      
     
       QueryResult<Expert> experts = null;
        //开始做不同错误的处理方式判断:
       if(request.getParameter("requestType")==null ){
    	   //是刷新进来的
    	   try {
    	    	 category  = request.getParameter("category")==null?7:Integer.parseInt(request.getParameter("category"));
    			 page.setPage(request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page")));
    	        } catch (NumberFormatException e) {
    			    //可以传输错误的 值跳转到特殊的页面
    	        	 response.sendRedirect("./admin_error.jsp"); 
    	        	 return ;
    	        }
    	      try {
    	    	 
    	    	  experts=ExpertServiceImpl.getInstance().showExpertInfo(category, page.getPage(), page.getMaxSize());
    	    	  filterPassword(experts);
    		    } catch (ErrorException e) {
    		    	  request.setAttribute("errorMesg",e.getMessage());
    		          request.getRequestDispatcher("./admin_error.jsp").forward(request, response);
    		          return ;
    	          } catch (DBException e) {
    	        	  request.setAttribute("errorMesg",e.getMessage());
    		          request.getRequestDispatcher("./admin_error.jsp").forward(request, response);
    		          return ;
    	         } 
    	      page.setCount(experts==null?0:(int)experts.getTotalCount());
    	      request.setAttribute("page",page);
    	      request.setAttribute("experts", experts);
    	      request.setAttribute("category", category);  //用来确定上面的类别
    	    
    	      if(requestUrl==null){
    	    	  
    	    	  request.getRequestDispatcher("./admin_viewMangeAction.jsp").forward(request, response);
    	      }else{
    	     
    	    	  request.getRequestDispatcher("./admin_ExpertInfo.jsp").forward(request, response);
    	      }
    	   
       }else if(request.getParameter("requestType").equals("AJAX")) {
    		response.setContentType("application/json");  
    		PrintWriter  out  = response.getWriter(); 
    		int  maxSize =0 ;
    	   //这个就是ajax的处理方式
    	     try {
    	    	 category  = request.getParameter("category")==null?7:Integer.parseInt(request.getParameter("category"));
    			 page.setPage(request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page")));
    			 maxSize=  request.getParameter("maxSize")==null?page.getMaxSize():10000;
    	        } catch (NumberFormatException e) {
    			    //可以传输错误的 值跳转到特殊的页面
    	        	out.print("{\"errorMesg\":\"数据格式错误\"}");
    		    	out.flush();
    		    	out.close();
    	        	 return ;
    	        }
    	
    		  try {
    	    	  experts=ExpertServiceImpl.getInstance().showExpertInfo(category, page.getPage(), maxSize);
    	    	  filterPassword(experts);
    		    } catch (ErrorException e) {
    		    	 //通过json解析拿到errorMesg  来弹窗提醒
    		    	out.print("{\"errorMesg\":\""+e.getMessage()+"\"}");
    		    	out.flush();
    		    	out.close();
    		    	 return ;
    	          } catch (DBException e) {
    	            out.print("{\"errorMesg\":\""+e.getMessage()+"\"}");
    	            out.flush();
    		    	out.close();
    		    	 return ;
    	         } 
    	   if(experts!=null){
    	        StringBuffer   buffer = new StringBuffer("[");
                Gson gson = new Gson();
    		   buffer.append(gson.toJson(experts));
    		  buffer.append(",").append(gson.toJson(page));
    		   buffer.append("]");
    		  out.print(buffer.toString());
    		  }else{
    		   out.print("{\"errorMesg\":\"不好意思,没有了\"}");
    	   }	  
    	   out.flush();
    	   out.close();
       }else{
    	     //属于故意攻击型
    		 response.sendRedirect("./admin_error.jsp");    
    	 }
       
        
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

       this.doGet(request, response);
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
	private   void filterPassword(QueryResult<Expert> experts){
		if(experts!=null){
		Iterator<Expert>  expertIterator  = experts.getResults().iterator();
		while(expertIterator.hasNext()){
			Expert expert  = expertIterator.next();
			expert.setPassword("******");
		}
	}
	}
}
