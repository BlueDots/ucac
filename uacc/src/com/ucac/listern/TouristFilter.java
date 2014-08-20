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

import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Settings;
@WebFilter(value={"/touristsResult.jsp"})
public class TouristFilter  implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		int touristState = 0;
		HttpServletRequest request =   (HttpServletRequest) req;	 
		HttpServletResponse response = (HttpServletResponse) resp;
		
		EntityDao entity = EntityDaoImpl.getInstance();
			try {
				touristState = entity.findById(Settings.class, 1).getTouristState();
			} catch (ErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  if(touristState == 1){
			  chain.doFilter(request, response);
		  }else{
			  request.getRequestDispatcher("./login.jsp").forward(request, response);
		  }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
