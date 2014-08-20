package com.ucac.listern;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.ucac.lucene.Config.Configer;

@WebListener
public class ApplicationListenr implements ServletContextListener {
 
	/**
	 * 熊安平
	 * TODO
	 * @param arg0
	 * 实现思路:
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
       System.out.println("--------正在关闭-------");
       try {
		Configer.getIndexWriter().close();
		Configer.getAnalyzer().close();
		Configer.getDirectory().close();
	} catch (IOException e) {
	 	e.printStackTrace();
	}
	}

	/**
	 * 熊安平
	 * TODO
	 * @param arg0
	 * 实现思路:
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
       
	}

}
