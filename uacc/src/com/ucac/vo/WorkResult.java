package com.ucac.vo;
/**
 * 【游客，管理员，申报人员，专家评分结果】
　　　　比赛结果展示时用到的vo
 * @author Administrator
 *
 */
public class WorkResult {
	private int applicantId; //申报人id--------t_applicant
	private int category;//类别--------t_expert
	private String communityName;//社团名称--------t_applicant

	private double documentScore;//材料分数--------t_expert_assess
	private int expertId;//专家id--------t_expert
	private String expertName;//专家姓名--------t_expert
	private int id;//t_expert_assess的id
	private double pictureScore;//展板分数--------t_expert_assess
	private int ranking;//排名--------t_work
	private String school;//学校名称--------t_expert_assess
	private double score;//总分--------t_expert_assess
	private double videoScore;//视频分数--------t_expert_assess
	public int getApplicantId() {
		return applicantId;
	}
	public int getCategory() {
		return category;
	}
	public String getCommunityName() {
		return communityName;
	}
	public double getDocumentScore() {
		return documentScore;
	}
	public int getExpertId() {
		return expertId;
	}
	public String getExpertName() {
		return expertName;
	}
	public int getId() {
		return id;
	}
	public double getPictureScore() {
		return pictureScore;
	}
	public int getRanking() {
		return ranking;
	}
	public String getSchool() {
		return school;
	}
	public double getScore() {
		return score;
	}
	public double getVideoScore() {
		return videoScore;
	}
	public void setApplicantId(int applicantId) {
		this.applicantId = applicantId;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public void setDocumentScore(double documentScore) {
		this.documentScore = documentScore;
	}
	public void setExpertId(int expertId) {
		this.expertId = expertId;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPictureScore(double pictureScore) {
		this.pictureScore = pictureScore;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public void setVideoScore(double videoScore) {
		this.videoScore = videoScore;
	}
	
	@Override
	public String toString() {
		return "id:"+this.id+" |applicantId:"+this.applicantId+" |expertId:"+this.expertId+"|category:"+this.category+
				"|communityName:"+this.communityName+"|videoScore:"+this.videoScore+"|pictureScore:"+this.pictureScore+
				"|documentScore:"+this.documentScore+"|score:"+this.score+"|school:"+this.school+"|expertName:"+this.expertName
				+"|ranking:"+this.ranking;
	}
	
}