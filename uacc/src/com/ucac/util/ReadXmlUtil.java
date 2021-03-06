package com.ucac.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
 

public class ReadXmlUtil {
	private static SAXReader saxreader = null;
	private static Element rootElement = null;
	private static String path = "EntityTable.xml";
	static {
		saxreader = new SAXReader();
		// 首先我就拿到xml中的所有的Element的对象
		rootElement = getRootElementBypath(path);
	}

	

	private static Element getRootElementBypath(String path) {
		Element rootElement = null;
		// 拿到classpath下的文件
		InputStream inputStream = ReadXmlUtil.class.getClassLoader()
				.getResourceAsStream(path);
		Document document = null;
		try {
			document = saxreader.read(inputStream);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 拿到对象之后我关闭流
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 拿到root
		rootElement = document.getRootElement();
		return rootElement;
	}

	public static Element getRootElement() {
		return rootElement;
	}

	
	//这样子拿到的classElement就不需要再次的去读取xml了
	public static Element getClassElement(Class<?> t) {
		Element classElement = null;
		// 现在是拿到了root，但是我们要拿到的孩子,所有我们是先拿到孩子的迭代器
		Iterator<Element> childrensIterator = getRootElement().elementIterator();
		// 判断类是不是存在
		while (childrensIterator.hasNext()) {
			Element children = childrensIterator.next();
			if (t.getName().equals(children.attributeValue("className"))) {
				classElement = children;
				break;
			}
		}
		return classElement;
	}
	
	public static Boolean checkClassIsExists(Class<?> t) {
		Boolean result = false;
	 
		// 现在是拿到了root，但是我们要拿到的孩子,所有我们是先拿到孩子的迭代器
		Iterator<Element> childrensIterator = getRootElement().elementIterator();
		// 判断类是不是存在
		while (childrensIterator.hasNext()) {
			Element children = childrensIterator.next();
			if (t.getName().equals(children.attributeValue("className"))) {
				result = true;
				break;
			}
		}
		 
		return result;
 
	}

	/**
	  public static Boolean checkClassIsExists(String path, Class<?> t) {
	 

		Boolean result = false;
		// 拿到classpath下的文件
		InputStream inputStream = ReadXmlUtil.class.getClassLoader()
				.getResourceAsStream(path);
		Document document = null;
		try {
			document = saxreader.read(inputStream);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 拿到root
		Element element = document.getRootElement();
		// 现在是拿到了root，但是我们要拿到的孩子,所有我们是先拿到孩子的迭代器
		Iterator<Element> childrensIterator = element.elementIterator();
		// 判断类是不是存在
		while (childrensIterator.hasNext()) {
			Element children = childrensIterator.next();
			if (t.getName().equals(children.attributeValue("className"))) {
				result = true;
				break;
			}
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 通过实体类拿到 Element 当然这个path现在来说是不需要了的
	public static Element getClassElement(String path, Class t) {
		Element classElement = null;
		// 拿到classpath下的文件
		InputStream inputStream = ReadXmlUtil.class.getClassLoader()
				.getResourceAsStream(path);
		Document document = null;
		try {
			document = saxreader.read(inputStream);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 拿到root
		Element element = document.getRootElement();
		// 现在是拿到了root，但是我们要拿到的孩子,所有我们是先拿到孩子的迭代器
		Iterator<Element> childrensIterator = element.elementIterator();
		// 判断类是不是存在
		while (childrensIterator.hasNext()) {
			Element children = childrensIterator.next();
			if (t.getName().equals(children.attributeValue("className"))) {
				classElement = children;
				break;
			}
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classElement;
	}
***/
}
