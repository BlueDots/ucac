package com.ucac.yiwei.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ucac.vo.Log;

public class ReadLog {
	/**
	 * 
	 * yiwei
	 * 
	 * @todo :根据日期读取匹配的日志内容
	 * @param date:日期
	 * @return:符合日期的日志列表
	 * @throws IOException 
	 * @returntype List<Log>
	 */
	public static List<Log> readLogByLine(String date)
			throws IOException {
		File file = null;
		if(CompareTime.compareTime(date)) {
			file = new File("c:\\log\\log.log");
		}else {
			file = new File("c:\\log\\log.log." + String4Format.StringFormatOth(date));
			if(!(file.exists())) {
				file = new File("c:\\log\\log.log");
			}
		}
		List<Log> logs = new ArrayList<Log>();
		Log log = null;
		BufferedReader reader = null;
		FileReader fReader = new FileReader(file);
		try {
			reader = new BufferedReader(fReader);
			String tempString = null;

			while ((tempString = reader.readLine()) != null) {
				String[] strs = tempString.split(" ");
				log = new Log();
				if (date.equals(strs[0])) {
					log.setDate(strs[0]);
					log.setOperator(strs[1]);
					log.setContent(strs[2]);
					logs.add(log);
				}
			}
			reader.close();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new IOException();
				}
			}
		}
		return logs;
	}
}
