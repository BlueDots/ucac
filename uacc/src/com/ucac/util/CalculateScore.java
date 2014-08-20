/**
 * 
 */
package com.ucac.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Priority;

import com.alibaba.druid.proxy.jdbc.JdbcParameter.TYPE;
import com.ucac.Work.service.impl.WorkServiceImpl;
import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.ExpertAssess;
import com.ucac.po.Settings;
import com.ucac.po.Work;
import com.ucac.vo.QueryResult;
import com.ucac.vo.WorkResult;

public class CalculateScore {

	public static String calculateScore(){
		String success = "计算成功!";
		String sql_work_where = "workState";  //work表的筛选条件
		String sql_exeprt_assess_where = "select a.* from t_expert_assess a," +
					"t_expert e,t_work w where a.expert_id = e.id and w.id = a.applicant_id " +
					"and expert_state = 1 and w.id = ? limit ?,?";//对某个作品的全部打分记录的sql语句 
		EntityDao entity = EntityDaoImpl.getInstance();
		List<Object> workParames = new ArrayList<Object>();   //Work的参数列表
	    workParames.add(1);  //对Work的参数进行赋值
	    List<Object> assessParames =  new ArrayList<Object>();   //expert_assess的参数列表
	    
	    Settings settings = new Settings();   //实例化setting对象
		List<Work> works = new ArrayList<Work>();  //实例化一组work对象
		List<ExpertAssess> expertAssesses = new ArrayList<ExpertAssess>();   //实例化一组expert_Assess对象
		try {
			settings = entity.findById(Settings.class, 1);
		} catch (ErrorException e1) {
			// TODO Auto-generated catch block
			new ErrorException("系统异常,请稍后再试");
			return null;
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			new DBException("系统异常,请稍后再试");
			return null;
		}   //获得settings对象
		try {
			try {
				works = entity.findAllEntity(Work.class, 0, 1000, null, sql_work_where, workParames, 0).getResults();
			} catch (DBException e) {
				// TODO Auto-generated catch block
				new DBException("系统异常,请稍后再试");
				return null;
			} //获得works对象
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			new DBException("系统异常,请稍后再试");
			return null;
		}
		for (int i = 0; i < works.size(); i++) {
			
			double totalScore = 0.00;
			double documentScore = 0.00;
			double pictureScore = 0.00;
			double videoScore = 0.00;
			
			assessParames.clear();
			assessParames.add(works.get(i).getId());
			
			try {
				expertAssesses = entity.findAllEntityByCompose(ExpertAssess.class, 0, 1000, sql_exeprt_assess_where, assessParames);
				if (expertAssesses.size()==0) {
					documentScore = 0;
					pictureScore = 0;
					videoScore = 0;
				}else{
					for (int j = 0; j < expertAssesses.size(); j++) {
						documentScore = documentScore + expertAssesses.get(j).getDocumentScore();
						pictureScore = pictureScore + expertAssesses.get(j).getPictureScore();
						videoScore = videoScore + expertAssesses.get(j).getVideoScore();
					}
					
					documentScore = documentScore/expertAssesses.size();
					pictureScore = pictureScore/expertAssesses.size();
					videoScore = videoScore/expertAssesses.size();
				}
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			totalScore = totalScore + (documentScore * settings.getDocument() + pictureScore * settings.getPicture()
					     + videoScore * settings.getVideo()) * 0.01;
		
			works.get(i).setId(works.get(i).getId());
			works.get(i).setDocumentScore(documentScore);
			works.get(i).setPictureScore(pictureScore);
			works.get(i).setVideoScore(videoScore);
			works.get(i).setScore(totalScore);
			
			try {
				entity.update(works.get(i));
			} catch (ErrorException e) {
				// TODO Auto-generated catch block
				new DBException("系统异常,请稍后再试");
				return null;
			} catch (DBException e) {
				// TODO Auto-generated catch block
				new DBException("系统异常,请稍后再试");
				return null;
			}
		}
		return success;
	}
	public static String calculateRanking(){
		String success = "排序成功!";
		String work_sql;
		int count = 1;
		boolean flag = false;
		work_sql = "select * from t_work order by score desc,video_score desc,picture_score desc,document_score limit ?,?";
		EntityDao entity = EntityDaoImpl.getInstance();
		List<Work> works = new ArrayList<Work>();  //实例化一组work对象
		try {
			works = entity.findAllEntityByCompose(Work.class, 0, 10000, work_sql, null);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			new DBException("系统异常,请稍后再试");
			return null;
		}
		for (int i = 0; i < works.size(); i++) {	
			if(i==0) {
				works.get(i).setRanking(count);
			}else {
				if(works.get(i).getScore() == works.get(i-1).getScore()) {
					if(works.get(i).getVideoScore() == works.get(i-1).getVideoScore() ) {
						if(works.get(i).getDocumentScore() == works.get(i-1).getDocumentScore()){
							if(works.get(i).getPictureScore() == works.get(i-1).getPictureScore()){
								works.get(i).setRanking(works.get(i-1).getRanking());	
							}
							else if(works.get(i).getPictureScore() < works.get(i-1).getPictureScore()){
								works.get(i).setRanking(count);
							}
						}
						else if(works.get(i).getDocumentScore() < works.get(i-1).getDocumentScore()){
							works.get(i).setRanking(count);
						}
					}else if(works.get(i).getVideoScore() < works.get(i-1).getVideoScore()){
						works.get(i).setRanking(count);
					}
					
				}else {
					works.get(i).setRanking(count);
				}
			}
			count++;
			System.out.print("count:"+count);
		}
		for(int i=0;i<works.size();i++)
			try {
				entity.update(works.get(i));
			} catch (ErrorException | DBException e) {
				// TODO Auto-generated catch block
				new DBException("系统异常,请稍后再试");
				return null;
			}
		
		return success;
	}
	
	public static QueryResult<WorkResult> calculateItemRanking(int page,int maxSize,int category,int sortType){
		
		int count = 1;
		//EntityDao entity = EntityDaoImpl.getInstance();
		//String work_sql = "select * from t_work where order by score desc,video_score desc,picture_score desc,document_score limit ?,?";
		if (page > 1) {
			//priorScore = entity.findById(Work.class,)
			count = (page - 1) * maxSize + 1;
		}
	    
		QueryResult<WorkResult> queryResult = new QueryResult<WorkResult>();
		
		try {
			queryResult = WorkServiceImpl.getInstance().showWorkResult(
					page, maxSize, category, sortType);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WorkResult> works = queryResult.getResults();
		
		for (int i = 0; i < works.size(); i++) {	
			if(i==0) {
				works.get(i).setRanking(count);
			}else {
				if(works.get(i).getScore() == works.get(i-1).getScore()) {
					if(works.get(i).getVideoScore() == works.get(i-1).getVideoScore() ) {
						if(works.get(i).getDocumentScore() == works.get(i-1).getDocumentScore()){
							if(works.get(i).getPictureScore() == works.get(i-1).getPictureScore()){
								works.get(i).setRanking(works.get(i-1).getRanking());	
							}
							else if(works.get(i).getPictureScore() < works.get(i-1).getPictureScore()){
								works.get(i).setRanking(count);
							}
						}
						else if(works.get(i).getDocumentScore() < works.get(i-1).getDocumentScore()){
							works.get(i).setRanking(count);
						}
					}else if(works.get(i).getVideoScore() < works.get(i-1).getVideoScore()){
						works.get(i).setRanking(count);
					}
					
				}else {
					works.get(i).setRanking(count);
				}
			}
			count++;
			System.out.print("count:"+count);
		}
		
		return queryResult;
	}
}
