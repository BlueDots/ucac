package com.ucac.po;

import java.io.Serializable;

public class ExpertAssess  implements Serializable{
	private  Applicant applicant;

	private double documentScore;

	private  Expert expert;

	private int id;
	private double pictureScore;
	private double score;
	private double videoScore;
	public Applicant getApplicant() {
		return applicant;
	}
	public double getDocumentScore() {
		return documentScore;
	}
	public Expert getExpert() {
		return expert;
	}
	public int getId() {
		return id;
	}

	public double getPictureScore() {
		return pictureScore;
	}

	public double getScore() {
		return score;
	}
	public double getVideoScore() {
		return videoScore;
	}
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	public void setDocumentScore(double documentScore) {
		this.documentScore = documentScore;
	}
	
	public void setExpert(Expert expert) {
		this.expert = expert;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setPictureScore(double pictureScore) {
		this.pictureScore = pictureScore;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public void setVideoScore(double videoScore) {
		this.videoScore = videoScore;
	}
	
	@Override
	public String toString() {
		return "id:"+this.id+"|applicant:"+this.applicant.getId()+"|expert:"+this.expert.getId()+"|videoScore:"+this.videoScore
				+"|pictureScore:"+this.pictureScore+"|documentScore:"+this.documentScore+"|score:"+this.score;
	}
	
	
}
