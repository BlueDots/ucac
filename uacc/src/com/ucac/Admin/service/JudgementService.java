package com.ucac.Admin.service;

import java.util.Date;

import com.ucac.yiwei.exception.JudgementException;
//yiwei 
public interface JudgementService {
	
	
	/**
	 * 
	 * yiwei
	 * @todo :将从jsp页面中取出的时间数据进行格式匹配
	 * @param start:为每个表单的开始时间数据
	 * @param end:为每个表单的截止时间数据
	 * @return:true表示匹配成功 , false:表示匹配失败
	 * @throws JudgementException
	 * @returntype boolean
	 */
	public boolean judgementTimePattern(String start , String end)throws JudgementException;
	
	/**
	 * yiwei
	 * @todo:判断时间格式
	 * @param timestr:表单中的时间数据
	 * @return:true表示匹配成功 , false:表示匹配失败
	 * @throws JudgementException
	 */
	public boolean judgementTimePattern(String timestr) throws JudgementException;
	/**
	 * 
	 * yiwei
	 * @todo :判断设置的时间是否在当前系统时间之后
	 * @param nowDate:为当前系统时间
	 * @param date: 表单的设置时间
	 * @return:true表示设置的时间是否在当前系统时间之后 , false:为不是
	 * @throws JudgementException
	 * @returntype boolean
	 */
	public boolean judgementSettingBeginTime(Date nowDate, Date date)throws JudgementException;
	
	/**
	 * 
	 * yiwei
	 * @todo :判断设置的开始时间和截止时间是否有冲突
	 * @param start:开始时间
	 * @param end:截止时间
	 * @return true表示无冲突 , false:有冲突
	 * @throws JudgementException
	 * @returntype boolean
	 */
	public boolean judgementSetEndTime(Date start , Date end)throws JudgementException;
	
	/**
	 * 
	 * yiwei
	 * @todo :判断两个时间的先后循序
	 * @param front:排在前面的时间
	 * @param later:排在后面的时间
	 * @return:true表示front<later , false:则相反
	 * @throws JudgementException
	 * @returntype boolean
	 */
	public boolean judgementCompareTime(Date front , Date later)throws JudgementException;
	
	/**
	 * 
	 * yiwei
	 * @todo :判断比例是否为整数
	 * @param video:视频的比例值
	 * @param picture:展板的比例值
	 * @param document:文档的比例值
	 * @return:true表示是整数 ,false则表示不是比例值
	 * @throws JudgementException
	 * @returntype boolean
	 */
	public boolean judgementGetProportion(Object video , Object picture , Object document)throws JudgementException;
	
	/**
	 * 
	 * yiwei
	 * @todo :判断每一个比例值是否在0-100之间
	 * @param video:视频的比例值
	 * @param picture:展板的比例值
	 * @param document:文档的比例值
	 * @return:true表示是0-100 ,false则表示不是
	 * @throws JudgementException
	 * @returntype boolean
	 */
	public boolean judgementProportionSingleValue(Object video, Object picture, Object document)throws JudgementException;
	
	/**
	 * 
	 * yiwei
	 * @todo :判断比例值的和是否为100
	 * @param video:视频的比例值
	 * @param picture:展板的比例值
	 * @param document:文档的比例值
	 * @return:true表示是100 ,false则表示不是
	 * @throws JudgementException
	 * @returntype boolean
	 */
	public boolean judgementProportionValue(Object video, Object picture, Object document)throws JudgementException;
}
