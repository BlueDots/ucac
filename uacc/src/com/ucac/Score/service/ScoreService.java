package com.ucac.Score.service;

import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.ExpertAssess;
//
public interface ScoreService {
	
	
	 
	
	
	/**
	 * 插入分数  【专家打分页面】（陈江）
	 * @param expertAssess
	 * @return
	 * @throws ErrorException 
	 * @throws DBException 
	 */
	public int saveExpertAssess(ExpertAssess expertAssess) throws DBException, ErrorException;
	
	
	/**
	 * 更新分数  【专家打分页面】（陈江）
	 * @param expertAssess
	 * @return
	 * @throws DBException 
	 * @throws ErrorException 
	 */
	public int updateExpertAssess(ExpertAssess expertAssess) throws ErrorException, DBException;
	
	
	/**台专家计算3项的总分（陈江）
	 * 
	 * @param videoScore
	 * @param documentScore
	 * @param pictureScore
	 * @return
	 * @throws DBException 
	 * @throws ErrorException 
	 */
	public double calculateTotalScore(double videoScore,double documentScore,double pictureScore) throws ErrorException, DBException;
	
	public String calculateWorkScore() throws DBException, ErrorException;
	public String calculateWorkRanking() throws DBException, ErrorException;
}
