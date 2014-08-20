package com.ucac.yiwei.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Admin.service.AdminService;
import com.ucac.Admin.service.impl.AdminServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Settings;

@WebServlet("/manager/showSettingContent.do")
public class showSettingContentServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	
	public showSettingContentServlet() {
		super();
	}

	
	@Override
	public void destroy() {
		super.destroy();
	}

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * 
	 * yiwei
	 * @todo :将设置信息从数据库中取出
	 * @param request
	 * @param response:处理返回
	 * @returntype void
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		AdminService adminService = AdminServiceImpl.getInstance();
		Settings settings = null;
		try {
			settings = adminService.showSettingContent();
			
		String gson = "[{\"applyBegin\":"+"\""+settings.getApplyBegin().toString()+ "\"," + "\"applyEnd\":\""+settings.getApplyEnd().toString() +"\"," + "\"appraiseBegin\":" + "\""+settings.getAppraiseBegin().toString() 
					+ "\","+"\""+ "appraiseEnd\":"+ "\""+ settings.getAppraiseEnd().toString() + "\"," +"\""+ "document\":"+ settings.getDocument() + "," +"\""+ "picture\":"+ settings.getPicture() + "," +"\""+ "video\":" + settings.getVideo()
 					+"}]"; 
			System.out.println(gson);
			out.print(gson);
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

	
	@Override
	public void init() throws ServletException {
		
	}

}
