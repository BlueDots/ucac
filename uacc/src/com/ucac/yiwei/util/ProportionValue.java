package com.ucac.yiwei.util;

public class ProportionValue {
	public static int proportion2Int(String value) {
		String[] values = value.split("%");
		return Integer.parseInt(values[0]);
	}
}
