package com.ucac.util;
import org.apache.log4j.Logger;

public class WriteLog {
	/**
	 * yiwei
	 * @todo:书写日志内容到相应文件
	 * @param operator：操作人
	 * @param content：操作内容
	 */
	public static void writeLog(String operator , String content) {
		Logger logger = Logger.getLogger("mylog");
		logger.info(operator + " " + content);
	}
}
