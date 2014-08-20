package com.ucac.yiwei.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.Admin.service.AdminService;
import com.ucac.Admin.service.JudgementService;
import com.ucac.Admin.service.impl.AdminServiceImpl;
import com.ucac.Admin.service.impl.JudgementServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Settings;
import com.ucac.yiwei.exception.JudgementException;
import com.ucac.yiwei.util.ProportionValue;
import com.ucac.yiwei.util.String2Date;

@WebServlet("/manager/updateSettingContent.do")
public class updateSettingContentServlrt extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public updateSettingContentServlrt() {
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
	 * @todo :从表单中拿到管理员设置信息更新数据库
	 * @param request
	 *            :负责从表单中拿到信息
	 * @param response
	 *            :负责处理跳转
	 * @throws JudgementException
	 * @throws DBException
	 * @throws ErrorException
	 * @returntype void
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("formId"));
		JudgementService judgementService = JudgementServiceImpl.getInstance();
		AdminService adminService = AdminServiceImpl.getInstance();
		int temp = 0;
		Settings s = null;
		try {
			s = adminService.showSettingContent();
		} catch (ErrorException e) {
			// TODO Auto-generated catch block

		} catch (DBException e) {
			// TODO Auto-generated catch block
		}

		Settings setting = null;
		if (id == 1) {
			String applyBegin = request.getParameter("applyBegin");
			String applyEnd = request.getParameter("applyEnd");
			
			try {
				if(judgementService.judgementTimePattern(applyBegin, applyEnd)) {
					
					setting = new Settings();
					setting.setId(s.getId());
					setting.setApplyBegin(String2Date.formatDate(applyBegin));
					setting.setApplyEnd(String2Date.formatDate(applyEnd));
					setting.setVideo(s.getVideo());
					setting.setPicture(s.getPicture());
					setting.setDocument(s.getDocument());
					try {
						temp = adminService.modifySettingContent(setting, id);
						if(temp != 0) {
							out.print("修改成功");
						}else {
							out.print("修改失败");
						}
					} catch (ErrorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JudgementException e) {
						// TODO Auto-generated catch block
						out.print(e.getMessage());
					}
				} else {
					try {
						throw new JudgementException("时间格式错误");
					} catch (JudgementException e) {
						// TODO Auto-generated catch block
						out.print(e.getMessage());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				out.print(e.getMessage());
			}
		} else if (id == 2) {
			String appraiseBegin = request.getParameter("appraiseBegin");
			String appraiseEnd = request.getParameter("appraiseEnd");
			try {
				if (judgementService.judgementTimePattern(appraiseBegin,
						appraiseEnd)) {
					setting = new Settings();
					setting.setId(s.getId());
					setting.setAppraiseBegin((String2Date.formatDate(appraiseBegin)));
					setting.setAppraiseEnd(String2Date.formatDate(appraiseEnd));
					setting.setVideo(s.getVideo());
					setting.setPicture(s.getPicture());
					setting.setDocument(s.getDocument());
					try {
						temp = adminService.modifySettingContent(setting, id);
						if(temp != 0) {
							out.print("修改成功");
						}else {
							out.print("修改失败");
						}
					} catch (ErrorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JudgementException e) {
						// TODO Auto-generated catch block
						out.print(e.getMessage());
					}
				} else {
					try {
						throw new JudgementException("时间格式错误");
					} catch (JudgementException e) {
						// TODO Auto-generated catch block
						out.print(e.getMessage());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				out.print(e.getMessage());
			}
		} else if (id == 3) {

			int video = ProportionValue.proportion2Int(request
					.getParameter("video"));
			int document = ProportionValue.proportion2Int(request
					.getParameter("document"));
			int picture = ProportionValue.proportion2Int(request
					.getParameter("picture"));

			setting = new Settings();
			setting.setId(s.getId());
			setting.setVideo(video);
			setting.setPicture(picture);
			setting.setDocument(document);
			try {
				temp = adminService.modifySettingContent(setting, id);
				if(temp != 0) {
					out.print("修改成功");
				}else {
					out.print("修改失败");
				}
			} catch (ErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JudgementException e) {
				// TODO Auto-generated catch block
				out.print(e.getMessage());
			}
		}
	}

	@Override
	public void init() throws ServletException {

	}

}
