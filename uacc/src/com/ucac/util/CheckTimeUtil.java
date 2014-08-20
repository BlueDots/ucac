package com.ucac.util;

import java.util.Date;

import com.ucac.Admin.service.AdminService;
import com.ucac.Admin.service.JudgementService;
import com.ucac.Admin.service.impl.AdminServiceImpl;
import com.ucac.Admin.service.impl.JudgementServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Settings;
import com.ucac.yiwei.exception.JudgementException;


public class CheckTimeUtil {
	   
	static JudgementService judgementService = null;
	static {
		AdminService adminService = AdminServiceImpl.getInstance();
		judgementService = JudgementServiceImpl.getInstance();
	 
	}
	
	/**
	 * 
	 * yiwei
	 * @todo :判断申报人员是在可以登录时间内
	 * @return:true:可以登录,false:不可登陆
	 * @throws DBException 
	 * @throws ErrorException 
	 * @returntype boolean
	 */
	public static boolean checkApplyLogin() throws ErrorException, DBException {
		Settings s = AdminServiceImpl.getInstance().showSettingContent();
		boolean flag = false;
		try {
			if(judgementService.judgementCompareTime(s.getApplyBegin() , new Date())) {
				flag = true;
			}
		} catch (JudgementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 
	 * yiwei
	 * @todo :判断申报人员是在可以上传作品时间内
	 * @return:true:可以上传,false:不可上传
	 * @throws DBException 
	 * @throws ErrorException 
	 * @returntype boolean
	 */
	public static boolean checkUpLoad() throws ErrorException, DBException {
		Settings s = AdminServiceImpl.getInstance().showSettingContent();
		boolean flag = false;
		try {
			if(judgementService.judgementCompareTime(s.getApplyBegin() , new Date()) && judgementService.judgementCompareTime(new Date(), s.getApplyEnd())) {
				flag = true;
			}
		} catch (JudgementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * 
	 * yiwei
	 * @todo :判断专家是在可以登录时间内
	 * @return:true:可以登录,false:不可登陆
	 * @throws DBException 
	 * @throws ErrorException 
	 * @returntype boolean
	 */
	public static boolean checkAppraiseLogin() throws ErrorException, DBException {
		Settings s = AdminServiceImpl.getInstance().showSettingContent();
		boolean flag = false;
		try {
			if(judgementService.judgementCompareTime(s.getAppraiseBegin() , new Date()) && judgementService.judgementCompareTime(new Date(), s.getAppraiseEnd())) {
				flag = true;
			}
		} catch (JudgementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	
}
