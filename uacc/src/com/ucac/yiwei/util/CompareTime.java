package com.ucac.yiwei.util;

import java.util.Date;

public class CompareTime {
	
	public static boolean compareTime(String date) { 
		boolean flag = false;
		if(date.equals(Date2String.Date4FormatOth(new Date()))) {
			flag = true;
		}
		return flag;
	}
	   
}
