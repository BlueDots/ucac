package com.ucac.Admin.service.impl;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ucac.Admin.service.JudgementService;
import com.ucac.yiwei.exception.JudgementException;

 //yiwei
public class JudgementServiceImpl implements JudgementService {

	private static class JudgementServiceHelper {
		static final JudgementService INSTANCE = new JudgementServiceImpl();
	}

	public static JudgementService getInstance() {
		return JudgementServiceHelper.INSTANCE;
	}

	@Override
	public boolean judgementTimePattern(String start, String end) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile("^([0-9]{4})[/-]([0]?[1-9]|[1|2][0-9]|[3][0|1])[/-]([0]?[1-9]|[1|2][0-9]|[3][0|1])$");
		Matcher startM = pattern.matcher(start);
		Matcher endM = pattern.matcher(end);
		if(startM.matches() && endM.matches()) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean judgementSettingBeginTime(Date nowDate, Date date) {
		// TODO Auto-generated method stub
		return date.after(nowDate);
	}

	@Override
	public boolean judgementSetEndTime(Date start, Date end) {
		// TODO Auto-generated method stub
		return end.after(start);
	}
	
	@Override
	public boolean judgementGetProportion(Object video, Object picture,
			Object document) {
		// TODO Auto-generated method stub
		if (video.getClass().getName().equals("java.lang.Integer")
				&& picture.getClass().getName().equals("java.lang.Integer")
				&& document.getClass().getName().equals("java.lang.Integer")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean judgementProportionSingleValue(Object video, Object picture,
			Object document) {
		// TODO Auto-generated method stub
		if (Integer.parseInt(video.toString()) < 100
				&& Integer.parseInt(video.toString()) > 0
				&& Integer.parseInt(picture.toString()) < 100
				&& Integer.parseInt(picture.toString()) > 0
				&& Integer.parseInt(document.toString()) < 100
				&& Integer.parseInt(document.toString()) > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean judgementProportionValue(Object video, Object picture,
			Object document) {
		// TODO Auto-generated method stub
		int allValue = Integer.parseInt(video.toString())
				+ Integer.parseInt(picture.toString())
				+ Integer.parseInt(document.toString());
		if (allValue == 100) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean judgementCompareTime(Date front, Date later) {
		// TODO Auto-generated method stub
		return later.after(front);
	}

	@Override
	public boolean judgementTimePattern(String timestr)
			throws JudgementException {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile("^([0]?[1-9]|[1|2][0-9]|[3][0|1])[/-]([0]?[1-9]|[1|2][0-9]|[3][0|1])[/-]([0-9]{4})$");
		Matcher matcher = pattern.matcher(timestr);
		if(matcher.matches()) {
			return true;
		}else {
			return false;
		}
	
	}

}
