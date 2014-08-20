package com.ucac.vo;
/**
 * //展示申报人员作品使用的vo 
 * @author Administrator
 *
 */
public class ApplicantWork {
	private int applicantId;
	private short category;
	private String communityName;
	private double documentScore;
	private double pictureScore;
	private String school;
	private double score;
	private double videoScore;
	public int getApplicantId() {
		return applicantId;
	}
	public short getCategory() {
		return category;
	}
	public String getCommunityName() {
		return communityName;
	}
	public double getDocumentScore() {
		return documentScore;
	}
	public double getPictureScore() {
		return pictureScore;
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
	public void setCategory(short category) {
		this.category = category;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public void setDocumentScore(double documentScore) {
		this.documentScore = documentScore;
	}
	public void setPictureScore(double pictureScore) {
		this.pictureScore = pictureScore;
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
	
}
