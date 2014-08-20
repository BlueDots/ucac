package com.ucac.li.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ucac.Work.service.impl.WorkServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.util.CalculateScore;
import com.ucac.vo.Page;
import com.ucac.vo.QueryResult;
import com.ucac.vo.WorkResult;
@WebServlet("/showWorkResult.do")
public class ShowWorkResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 李轶翔
	 * @todo    显示比赛结果
	 * @param request
	 * @param response
	 * @return	
	 * @returntype QueryResult<WorkResult>
	 */
    
	public ShowWorkResultServlet() {
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

				PrintWriter out = response.getWriter();
				
				//分页显示
				Page pages = new Page();
			
				//每页显示多少条记录
				int maxSize = 5;			
				
				String requestURL = request.getParameter("requestURL");
				
				if(requestURL==null){
					
					out.print("您输入的路径不合法，请重新输入或退出！");
					
				}else{
									
					//是否按类别显示
					int category = Integer.parseInt(request.getParameter("category"));
				
					
					//升降序
					int sortType = Integer.parseInt(request.getParameter("sortType"));
							
				
						// 调用service的方法 接收返回值
						QueryResult<WorkResult> queryResult = new QueryResult<WorkResult>();
			
						// 返回值的保存 为json字符串
						// 拼接json字符串
						StringBuffer strB = new StringBuffer("[");
						// 抓异常
			
						try {
			
							// 当前显示第几页
							int page = Integer.parseInt(request.getParameter("nowpage"));
			
							if(category==7){
								queryResult = WorkServiceImpl.getInstance().showWorkResult(page, maxSize, category, sortType);
							}else {
								queryResult = CalculateScore.calculateItemRanking(page, maxSize, category, sortType);
							}
			
							// 分页问题
							// 设置当前页面
							pages.setPage(page);
							// 设置数据数量
							pages.setCount((int) queryResult.getTotalCount());
							// 设置每页最大显示数量
							pages.setMaxSize(maxSize);
			
							// List<Integer> LinkNum = pages.getLinkNum();
							/*
							 * for(Iterator<WorkResult> i =
							 * queryResult.getResults().iterator();i.hasNext();){
							 * 
							 * WorkResult workResult = i.next();
							 * 
							 * //名次 int ranking = workResult.getRanking(); //学校 String
							 * school = workResult.getSchool(); //社团 String communityName =
							 * workResult.getCommunityName(); //类别 int categoryType =
							 * workResult.getCategory(); //视频 double videoScore =
							 * workResult.getVideoScore(); //展板 double pictureScore =
							 * workResult.getPictureScore(); //申报材料 double documentScore =
							 * workResult.getDocumentScore(); //分数 double Score =
							 * workResult.getScore(); //查看作品细节（需要申报人员的id） int applicantId =
							 * workResult.getApplicantId();
							 * 
							 * strB.append("{\"ranking\":"+ranking+",\"school\":\""+school+
							 * "\",\"communityName\":\""
							 * +communityName+"\",\"categoryType\":"
							 * +categoryType+",\"videoScore\":"
							 * +videoScore+",\"pictureScore\":"
							 * +pictureScore+",\"documentScore\":"
							 * +documentScore+",\"Score\":"
							 * +Score+",\"applicantId\":"+applicantId+"},");
							 * 
							 * }
							 */
							/*
							 * strB.deleteCharAt(strB.length()-1); strB.append("]");
							 */
			
							// 使用gson
							Gson gson = new Gson();
							// 添加queryResult对象
							String json = gson.toJson(queryResult);
							strB.append(json);
							strB.append(",");
							// 添加pages对象
							String pageStr = gson.toJson(pages);
							strB.append(pageStr);
							strB.append(",");
							// 添加linkNum对象
							List<Integer> linkNum = pages.getLinkNum();
							strB.append("{\"linkNum\":");
							strB.append(gson.toJson(linkNum));
							strB.append("}");
							strB.append("]");
			
							out.print(strB);
			
						} catch (Exception e) {
							// TODO Auto-generated catch block
							out.print("抱歉，系统错误");
							e.printStackTrace();
						}
				
				}
				//返回json字符串,为String类型
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
