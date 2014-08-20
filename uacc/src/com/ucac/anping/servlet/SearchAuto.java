package com.ucac.anping.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Applicant.service.impl.ApplicantServiceImpl;
import com.ucac.Expert.service.impl.ExpertServiceImpl;
import com.ucac.po.Applicant;
import com.ucac.po.Expert;
import com.ucac.vo.QueryResult;

@WebServlet("/searchAuto.do")
public class SearchAuto extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SearchAuto() {
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
		response.setContentType("application/json");  
		 
		String text = URLDecoder.decode(request.getParameter("wordtext"),
				"utf-8");
		String flag= request.getParameter("condition");
		if (text != null) {
			String  string = null;
          if(flag.equals("school")){
        	  string =this.getSchoolName(text.trim());
        	  if(string!=null){
                PrintWriter out = response.getWriter();
                out.print(string);
                out.flush();
                out.close();
        	  }
        	  
          } else if(flag.equals("communityName")){
        	  string = this.getComminityName(text.trim());
        	  if(string!=null){
        		  PrintWriter out = response.getWriter();
                  out.print(string);
                  out.flush();
                  out.close();
        	  }
          }  else if(flag.equals("expert")){
        	  string  = this.getExpertName(text.trim());
        	  if(string!=null){
        		  PrintWriter out = response.getWriter();
                  out.print(string);
                  out.flush();
                  out.close();
        	  }
          }  
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
	
	private String  getSchoolName(String text){
		String a = null;
		QueryResult<Applicant> queryResult = ApplicantServiceImpl.getInstance().getExpertByLucene(text, 0, 100);
		
	
		if (queryResult != null) {
			queryResult = autoFilterAllTheSameSchoolNameOrCommiuty(queryResult,1);
		  	StringBuffer buffer = new StringBuffer("[");
			for (Applicant applicant : queryResult.getResults()) {
				buffer.append("{\"word\":").append("\"")
						.append(applicant.getSchool());
				buffer.append("\"").append("}").append(",");
			}
              if(!buffer.toString().equals("[")){
			buffer.deleteCharAt(buffer.toString().lastIndexOf(','));
              }
			buffer.append("]");
			a =buffer.toString();
		}
		
		return  a;
	}

	private String getComminityName(String text){
		String a = null;
		QueryResult<Applicant> queryResult = ApplicantServiceImpl.getInstance().getExpertByLucene(text, 0, 100);
		
	
		if (queryResult != null) {
			queryResult = autoFilterAllTheSameSchoolNameOrCommiuty(queryResult,0);
		  	StringBuffer buffer = new StringBuffer("[");
			for (Applicant applicant : queryResult.getResults()) {
				buffer.append("{\"word\":").append("\"")
						.append(applicant.getCommunityName());
				buffer.append("\"").append("}").append(",");
			}

			 if(!buffer.toString().equals("[")){
					buffer.deleteCharAt(buffer.toString().lastIndexOf(','));
		              }
			buffer.append("]");
			a =buffer.toString();
		}
		
		return  a;
	} 
	
	private String getExpertName(String text){
		String a  = null;
		QueryResult<Expert> queryResult = ExpertServiceImpl.getInstance()
				.getExpertByLucene(text, 0, 100);

		if (queryResult != null) {
		  	StringBuffer buffer = new StringBuffer("[");
			for (Expert expert : queryResult.getResults()) {
				buffer.append("{\"word\":").append("\"")
						.append(expert.getExpertName());
				buffer.append("\"").append("}").append(",");
			}

			 if(!buffer.toString().equals("[")){
					buffer.deleteCharAt(buffer.toString().lastIndexOf(','));
		              }
			buffer.append("]");
			a =buffer.toString();
		}
		  return a;
	}
	/**1代表过滤相同的学校  0 代表社团*/
	public QueryResult<Applicant>  autoFilterAllTheSameSchoolNameOrCommiuty(QueryResult<Applicant> applicants,int flag){
		List<Applicant> applicantList  = applicants.getResults();
		Iterator<Applicant> applicantIterator = applicantList.iterator();
		while(applicantIterator.hasNext()){
			Applicant applicant  = applicantIterator.next();
			System.out.println(applicant.getCommunityName()+applicant.getUsername()+applicant.getSchool());
			if(!autoFilteroneSameSchoolOrCommiuty(applicant,applicantList,flag)){
				applicantList.remove(applicant);
				applicantIterator=applicantList.iterator();
			}
		
		}
		 return applicants;
	}
	public boolean  autoFilteroneSameSchoolOrCommiuty(Applicant applicant,List<Applicant> applicants,int flag){
		boolean   a  = true;
		Iterator<Applicant> applicantIterator  = applicants.iterator();
		while(applicantIterator.hasNext()){
			Applicant key  = applicantIterator.next();
			if(!key.getUsername().equals(applicant.getUsername())){
				//如果社团是相同的话我们就返回false
				 if(flag==0 && applicant.getCommunityName().equals(key.getCommunityName())){
					 a=false;
					 break;
				 }else if(flag==1 && applicant.getSchool().equals(key.getSchool())){
				//如果学校
					 a=false;
					 break;
				 }
			  }
		}
		return a;
	}
}
