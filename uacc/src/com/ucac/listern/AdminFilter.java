package com.ucac.listern;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ucac.po.Admin;

@WebFilter(value={"/manager/*"})
public class AdminFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
 
		HttpServletRequest request =   (HttpServletRequest) req;	 
		HttpServletResponse response = (HttpServletResponse) resp;
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		
		if(admin!=null || request.getServletPath().equals("/login.do")){
     	//放行
		 
			chain.doFilter(request, response);
		}else  if(admin==null){
			 
	        request.getRequestDispatcher("/manager/login.jsp").forward(request, response);
	  	}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
