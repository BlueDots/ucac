package com.ucac.yiwei.util;

public class String4Format {
	/**
	 * 
	 * yiwei
	 * @todo :将格式为MM/dd/yyyy的日期字符串转换为日期为yyyy/MM/dd格式的字符串
	 * @param date:格式为MM/dd/yyyy的日期字符串
	 * @return:格式为yyyy/MM/dd日期字符串
	 * @returntype String
	 */
	public static String StringFormat(String date) {
		String[] strs = date.split("/");
		return strs[2] + "/" + strs[0] + "/" + strs[1];
	}
	
	public static String StringFormatOth(String date) {
		String[] strs = date.split("/");
		return strs[2] + "-" + strs[0] + "-" + strs[1];
	}
}
