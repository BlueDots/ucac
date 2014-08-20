package com.ucac.yiwei.util;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Date2String {
	/**
	 * 
	 * yiwei
	 * @todo :将从数据库中取出的date数据转换成yyyy/MM/dd格式
	 * @param date :从数据库中取出的date数据
	 * @return :转换后的指定格式的时间数据字符串
	 * @returntype String
	 */
	public static String Date4Format(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(date);
	}
	
	public static String Date4FormatOth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(date);
	}
}
