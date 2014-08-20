package com.ucac.sgp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ucac.Expert.service.ExpertService;
import com.ucac.Expert.service.impl.ExpertServiceImpl;
import com.ucac.Work.service.WorkService;
import com.ucac.Work.service.impl.WorkServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.vo.Page;
import com.ucac.vo.QueryResult;
import com.ucac.vo.WorkInfo;

@WebServlet("/ShowWorkInfos.do")
public class ShowWorkInfoServlet extends HttpServlet{
	private ExpertService expertService = ExpertServiceImpl.getInstance(); 
	private WorkService workService = WorkServiceImpl.getInstance();
	/**
	 * 宋国平
	 * TODO
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	/**
	 * 宋国平
	 * TODO
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		int count = 0;
		QueryResult<WorkInfo> wResult = null;
		Page page = new Page();
		page.setPage(request.getParameter("nowpage")==null?1:Integer.parseInt(request.getParameter("nowpage")));
		page.setMaxSize(5);
		try {
			wResult = workService.showWorkInfos(page.getPage(), page.getMaxSize());
		} catch (DBException e) {
			out.print("抱歉，系统错误");
			e.printStackTrace();
		}
		page.setCount((int)wResult.getTotalCount());
		
		StringBuffer strB = new StringBuffer("[");
		Gson gson = new Gson();
		// 添加queryResult对象
		String json = gson.toJson(wResult);
		strB.append(json);
		strB.append(",");
		// 添加pages对象
		String pageStr = gson.toJson(page);
		strB.append(pageStr);
		strB.append(",");
		// 添加linkNum对象
		List<Integer> linkNum = page.getLinkNum();
		strB.append("{\"linkNum\":");
		strB.append(gson.toJson(linkNum));
		strB.append("}");
		strB.append("]");

		System.out.print(strB);
		out.print(strB);
		out.flush();
		out.close();		
	}

	
	 
}
