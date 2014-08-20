package com.ucac.yiwei.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class String2Date {
	/**
	 * 
	 * yiwei
	 * @todo :将从jsp页面中取出的数据转换成date类型的数据
	 * @param dateTime :从jsp中取出的时间数据
	 * @return :转换之后的时间数据
	 * @returntype Date
	 */
	public static Date formatDate(String dateTime) {
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = sdf.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date formateDateOth(String dateTime) {
		SimpleDateFormat sdf =  new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		try {
			date = sdf.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
