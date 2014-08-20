package com.ucac.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {
	private static Properties properties;
	
	public static Properties getProperties(String fileName) {
		properties = new Properties();
		// 通过类加载器，用文件名拿到输入流
		InputStream is = PropUtil.class.getClassLoader().getResourceAsStream(
				fileName);
		try {
			// property载入输入流
			properties.load(is);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Properties file not found");
		} catch (IOException e) {
			throw new IllegalArgumentException("Properties file can not be loading ");
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties;
	}

	public static String getValue(String key) {
		return properties.getProperty(key);
	}
	
	public static String getValue(String key,String fileName) {
		properties = getProperties(fileName);
		return getValue(key);
	}
}