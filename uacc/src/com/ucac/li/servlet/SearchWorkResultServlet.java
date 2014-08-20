package com.ucac.li.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Work.service.impl.WorkServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.vo.QueryResult;
import com.ucac.vo.WorkResult;

@WebServlet("/searchWorkResult.do")
public class SearchWorkResultServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public SearchWorkResultServlet() {
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

	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		//设置编码
		
		PrintWriter out = response.getWriter();
		
		
		//接收参数
		String schoolName = request.getParameter("schoolName");
		String communityName = request.getParameter("communityName");
		
		//service方法返回值
		QueryResult<WorkResult> queryResult = new QueryResult<WorkResult>();

		//拼接json字符串
		StringBuffer strB  = new StringBuffer("[");
			
		try {
			
			//service方法
			queryResult = WorkServiceImpl.getInstance().searchWorkResult(schoolName, communityName);
			
			for(Iterator<WorkResult> i = queryResult.getResults().iterator();i.hasNext();){
				
				WorkResult workResult = i.next();
				
				//名次
				int ranking = workResult.getRanking();
				//学校
				String school = workResult.getSchool();
				//社团
				String searchCommunityName = workResult.getCommunityName();
				//类别
				int categoryType = workResult.getCategory();
				//视频
				double videoScore = workResult.getVideoScore();
				//展板
				double pictureScore = workResult.getPictureScore();
				//申报材料
				double documentScore = workResult.getDocumentScore();
				//分数
				double Score = workResult.getScore();
				//查看作品细节（需要申报人员的id）
				int applicantId = workResult.getApplicantId();
				
				strB.append("{\"ranking\":"+ranking+",\"school\":\""+school+"\",\"communityName\":\""+searchCommunityName+"\",\"categoryType\":"+categoryType+",\"videoScore\":"+videoScore+",\"pictureScore\":"+pictureScore+",\"documentScore\":"+documentScore+",\"Score\":"+Score+",\"applicantId\":"+applicantId+"},");
						
			}	
			
			String str = String.valueOf(strB);
			//json结果不为空
			if(!str.equals("[")){
				strB.deleteCharAt(strB.length()-1);
				strB.append("]");
				
				out.print(strB);
				
			//json结果为空
			}else{
				
				out.print("[]");
			}
			
		
							
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
