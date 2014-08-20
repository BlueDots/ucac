/**
 * @package_name   com.ucac.syw.servlet
 * @file_name GetCaptchaServlet.java
 * @author songyouwei
 * @date 2013-8-25
 */
package com.ucac.syw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.po.Applicant;
import com.ucac.po.Work;
import com.ucac.user.servic.UserService;
import com.ucac.user.service.impl.UserServiceImpl;
import com.ucac.util.WriteLog;
import com.ucac.vo.RegisterInfo;

/**
 * @ClassName: ActivationServlet
 * @Description: TODO 用户激活
 */
@WebServlet("/activation.do")
public class ActivationServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String message = "激活成功！";

		try {
			RegisterInfo registerInfo = new RegisterInfo();
			registerInfo.setUsername(request.getParameter("username"));
			registerInfo.setPassword(request.getParameter("password"));
			registerInfo.setPassword2(request.getParameter("password2"));
			registerInfo
					.setCommunityName(request.getParameter("communityName"));
			registerInfo.setCategory(Integer.parseInt(request
					.getParameter("category")));
			registerInfo.setTeacherName(request.getParameter("teacherName"));
			registerInfo.setTeacherPhone(request.getParameter("teacherPhone"));
			registerInfo.setTeacherEmail(request.getParameter("teacherEmail"));
			registerInfo.setVerificationCode(request
					.getParameter("verificationCode"));
			
			System.out.println(request.getParameter("teacherName"));
			
			UserService userService = UserServiceImpl.getInstance();
			

			List<Object> params = new ArrayList<>();
			params.add(request.getParameter("username"));
			Applicant applicant = EntityDaoImpl.getInstance().findEntity(Applicant.class, "username", params);
			if(applicant!=null && applicant.getStatus()==1){
				message="用户已激活,请不要重复激活";
				System.out.println("11111111111111111111111111111111111111111111111");
			}else if(applicant==null){
				message="用户不存在";
			}else if(applicant!=null&&  applicant.getStatus()==0){
			
			userService.register(registerInfo, request.getSession()
					.getAttribute("captcha").toString());
			
			
			//applicant存入session
//			request.getSession().setAttribute("applicant",applicant);
			
			//插入work
			Work work = new Work();
			work.setId(applicant.getId());
			EntityDaoImpl.getInstance().save(work);
			//log
			WriteLog.writeLog("申报人员ID为"+applicant.getId(), "创建作品");
			WriteLog.writeLog("申报人员ID为"+applicant.getId(), "注册");
			}
		} catch (Exception e) {
			message = e.getMessage();
			System.out.println("激活异常信息："+message);
		}

		out.print(message);
		out.flush();
		out.close();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

}
