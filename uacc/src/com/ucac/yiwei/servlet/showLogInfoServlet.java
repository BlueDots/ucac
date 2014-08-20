package com.ucac.yiwei.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ucac.Admin.service.JudgementService;
import com.ucac.Admin.service.LogService;
import com.ucac.Admin.service.impl.JudgementServiceImpl;
import com.ucac.Admin.service.impl.LogServiceImpl;
import com.ucac.vo.Log;
import com.ucac.yiwei.exception.JudgementException;
import com.ucac.yiwei.util.Date2String;

@WebServlet("/manager/showLogInfo.do")
public class showLogInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public showLogInfoServlet() {
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
	 * 
	 * @todo :输入时间，将该时间段内的日志信息从文件中一条条的读出
	 * @param request
	 *            :接受时间参数
	 * @param response
	 *            :返回处理
	 * @returntype void
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String datestr = request.getParameter("logdate");
		LogService logService = LogServiceImpl.getInstance();
		JudgementService jService = JudgementServiceImpl.getInstance();
		String gson = null;
		List<Log> logs = null;
		if (datestr.equals("")) {
			datestr = Date2String.Date4FormatOth(new Date());
		}
		try {
			if (jService.judgementTimePattern(datestr)) {
				try {
					try {
						logs = logService.showLogInfo(datestr);
						if (logs.size() == 0) {
							gson = "[{" + "\"" + "msg" + "\"" + ":" + "\""
									+ datestr + "无记录" + "\"" + "}]";
						} else {
							Gson g = new Gson();
							gson = g.toJson(logs);
							StringBuffer jsonBuffer = new StringBuffer(gson);
							gson = jsonBuffer.substring(0,
									jsonBuffer.length() - 1);
							gson = gson + "," + "{" + "\"" + "msg" + "\"" + ":"
									+ "\"" + "查找成功,共有" + logs.size() + "条符合的记录"
									+ "\"" + "}]";
						}

						out.print(gson);
						out.flush();
						out.close();
					} catch (FileNotFoundException e) {
						// TODO: handle exception
						gson = "[{" + "\"" + "msg" + "\"" + ":" + "\""
								+ "所读日志文件不存在" + "\"" + "}]";

						out.print(gson);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					gson = "[{" + "\"" + "msg" + "\"" + ":" + "\""
							+ e.getMessage() + "\"" + "}]";
					out.print(gson);
				}
			} else {
				throw new JudgementException("时间格式错误");
			}
		} catch (JudgementException e) {
			// TODO Auto-generated catch block
			gson = "[{" + "\"" + "msg" + "\"" + ":" + "\"" + e.getMessage()
					+ "\"" + "}]";
			out.print(gson);
		}

	}

	@Override
	public void init() throws ServletException {

	}

}
