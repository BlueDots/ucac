package com.ucac.Admin.service;

import java.io.IOException;
import java.util.List;

import com.ucac.vo.Log;
//yiwei
public interface LogService {
	/**
	 * 
	 * yiwei
	 * @todo :通过日期得到日志内容
	 * @param date:日期
	 * @return:读取的日志内容列表
	 * @throws IOException
	 * @returntype List<Log>
	 */
	public List<Log> showLogInfo(String date)throws IOException;
}
