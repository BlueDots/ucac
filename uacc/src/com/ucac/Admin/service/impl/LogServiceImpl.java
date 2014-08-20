package com.ucac.Admin.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ucac.Admin.service.LogService;
import com.ucac.vo.Log;
import com.ucac.yiwei.util.ReadLog;
//yiwei
public class LogServiceImpl implements LogService {

	private static class LogServiceHelper {
		final static LogService INSTANCE = new LogServiceImpl();
	}
	
	public static LogService getInstance() {
		return LogServiceHelper.INSTANCE;
	}
	
	@Override
	public List<Log> showLogInfo(String date) throws IOException,FileNotFoundException {
		// TODO Auto-generated method stub
		List<Log> logs = new ArrayList<Log>();
		logs = ReadLog.readLogByLine(date);
		return logs;
	}
	
}
