package com.ucac.Expert.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Expert;
import com.ucac.po.ExpertAssess;
import com.ucac.vo.QueryResult;
import com.ucac.vo.WorkResult;
//chengjiang  liyixiang 
public interface ExpertService {
	/**
	 * 
	 * 李轶翔
	 * @todo 		搜索单个专家信息（精确查询）
	 * @param expertName   专家姓名
	 * @return
	 * @returntype Expert
	 */
	public Expert searchSingleExpert(String expertName) throws ErrorException, DBException;


	/**
	 * 陈江
	 * 前台专家查看申报人详细打分信息
	 * @param applicantId
	 * @param expertId
	 * @return
	 * @throws DBException 
	 * @throws ErrorException 
	 */
	public ExpertAssess getExpertAssess(int applicantId,int expertId) throws ErrorException, DBException ;
	
	

	/**
	 * 
	 * 陈江
	 * @todo 在前台专家，后台专家打分记录模块的查看和搜索
	 * @param expertId
	 * @param category
	 * @param nowpage
	 * @param pageSize
	 * @param isFront true：前台专家，false:后台
	 * @return
	 * @throws DBException
	 * @returntype List<WorkResult>
	 */
	public List<WorkResult> getWorksInfo(int expertId,int category,int nowpage,int pageSize,Boolean isFront) throws DBException;

	
	/**
	 * 
	 * 陈江
	 * @todo 
	 * @param expert
	 * @return
	 * @throws ErrorException
	 * @throws DBException
	 * @returntype int
	 */
	public int modifyAccountState(Expert expert) throws ErrorException, DBException;
	
	 
	
	/**
	 * 
	 * 陈江
	 * @todo 得到WorkResult的总记录数
	 * @param expertId
	 * @param category
	 * @param isFront
	 * @return
	 * @throws DBException
	 * @returntype int
	 */
	public String numofWorkResult(int expertId, int category,Boolean isFront) throws DBException;

	/**
	 * 
	 * 熊安平
	 * TODO :用来批量的添加专家帐号
	 * @param experts  批量的专家帐号
	 * @return  返回给view层的数据 里面是数据库中所有的专家帐号
	 * @throws DBException 
	 * @throws ErrorException 
	 */
	public  QueryResult<Expert> addExpertInfo(List<Expert>  experts) throws ErrorException, DBException  ;
	/**
	 * 
	 * 熊安平
	 * TODO : 显示专家账号和（打分记录界面）专家账号查看
	 * @param category  专家的类别
	 * @param page      需要查看的专家页面
	 * @param maxSize   需要显示在页面显示的数量
	 * @return  返回的是给view层的数据 里面是数据库中所有的专家帐号
	 * @throws DBException 
	 * @throws ErrorException 
	 */
	public  QueryResult<Expert> showExpertInfo(int category,int page ,int  maxSize) throws ErrorException, DBException;
	/**
	 * 
	 * 熊安平
	 * TODO :　修改专家账号 
	 * @param experts　批量的专家帐号
	 * @return　返回的是更新的数量
	 * @throws DBException 
	 * @throws ErrorException 
	 */
	public  int modifyExpertInfo(List<Expert> experts) throws ErrorException, DBException;  
	
	public  QueryResult<Expert> getExpertByLucene(String queryString,int firstIndex,int maxSize); 

public  HSSFWorkbook exportHSSFbook(int category ) throws ErrorException, DBException ;
	
	public  HSSFWorkbook exportExpertRecord(int expertId,int category) throws DBException;
	
	public  HSSFWorkbook exportExcelWorkResult(int category,int sortType) throws DBException;
}
