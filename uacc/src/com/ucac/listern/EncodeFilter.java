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

 
@WebFilter("/*")
public class EncodeFilter implements Filter {

	@Override
	public void destroy() {
	 
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	 HttpServletRequest  request2  = (HttpServletRequest)request;
	 HttpServletResponse  response2  = (HttpServletResponse) response;
			 request2.setCharacterEncoding("UTF-8");
			 response2.setCharacterEncoding("UTF-8");
	 chain.doFilter(request2, response2);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

 

}
