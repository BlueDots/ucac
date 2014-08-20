package com.ucac.Score.service.impl;
 

 
import java.math.BigDecimal;

import com.ucac.Score.service.ScoreService;
import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Applicant;
import com.ucac.po.Expert;
import com.ucac.po.ExpertAssess;
import com.ucac.po.Settings;
import com.ucac.util.CalculateScore;
 

public class ScoreServiceImpl  implements ScoreService{
	private   EntityDao entityDao = EntityDaoImpl.getInstance();
	private static class ScoreServiceHelper {
		static final ScoreService INSTANCE = new ScoreServiceImpl();
	}

	public static ScoreService getInstance() {
		return ScoreServiceHelper.INSTANCE;
	}
	@Override
	public int saveExpertAssess(ExpertAssess expertAssess) throws DBException, ErrorException {
		int insertCount;
			insertCount = entityDao.save(expertAssess);
		return insertCount;
	}

	@Override
	public int updateExpertAssess(ExpertAssess expertAssess) throws ErrorException, DBException {
		int updateCount;
		updateCount = entityDao.update(expertAssess);

		return updateCount;
	}

	@Override
	public double calculateTotalScore(double videoScore, double documentScore,
			double pictureScore) throws ErrorException, DBException {
		double score;
		Settings settings;
		
		settings = entityDao.findById(Settings.class, 1);
		 
		score = videoScore*settings.getVideo()/100+documentScore*settings.getDocument()/100
				+pictureScore*settings.getPicture()/100;
		BigDecimal b = new BigDecimal(score);  
		score = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return score;
	}

	
 
	@Override
	public String calculateWorkScore() throws DBException, ErrorException {
		 return CalculateScore.calculateScore();
	}

	@Override
	public String calculateWorkRanking() throws DBException,ErrorException {
		 return CalculateScore.calculateRanking();
	}
	
	public ExpertAssess createExpertAssess(double videoScore,double pictureScore,double documentScore,Double score,int expertId,int applicantId) {
		ExpertAssess expertAssess = new ExpertAssess();
		Applicant applicant = new Applicant();
		applicant.setId(applicantId);
		Expert expert = new Expert();
		expert.setId(expertId);
		expertAssess.setApplicant(applicant);
		expertAssess.setExpert(expert);
		expertAssess.setVideoScore(videoScore);
		expertAssess.setPictureScore(pictureScore);
		expertAssess.setDocumentScore(documentScore);
		expertAssess.setScore(score);
		return expertAssess;
	}
	 
}
