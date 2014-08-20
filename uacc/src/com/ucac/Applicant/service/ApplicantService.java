package com.ucac.Applicant.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Applicant;
import com.ucac.vo.QueryResult;

public interface ApplicantService {

	/**
	 * 
	 * 李轶翔
	 * @todo     搜索申报人员
	 * @param applyName  参数为申报人员账号
	 * @return
	 * @returntype Applicant
	 */
	public Applicant showApplyInfo(String applyName) throws ErrorException, DBException;

	
	/****
	 * 
	 * 熊安平  
	 * TODO  使用lucene的搜索来做提示的
	 * @param queryString
	 * @param firstIndex
	 * @param maxSize
	 * @return
	 *
	 */
	public  QueryResult<Applicant> getExpertByLucene(String queryString,int firstIndex,int maxSize); 
	/**
	 * 
	 * 熊安平  
	 * TODO 使用电子表格俩生成专家的帐号
	 * @param excel
	 * @return 返回0和1   0  代表的是电子表格不何规矩
	 * @throws DBException 
	 * @throws ErrorException 
	 *
	 */
	public  int addExperts(HSSFWorkbook excel) throws ErrorException, DBException;
	/**
	 * 
	 * 熊安平  
	 * TODO拿到所有的applicant 可以用来做导出的
	 * @return
	 * @throws DBException 
	 * @throws ErrorException 
	 *
	 */
	public QueryResult<Applicant> getAllApplicants() throws ErrorException, DBException;
	/**
	 * 
	 * 熊安平    拿到所有的账户
	 * TODO
	 * @return
	 * @throws DBException 
	 * @throws ErrorException 
	 *
	 */
	public HSSFWorkbook getExcelByApplicant() throws ErrorException, DBException;
 }
