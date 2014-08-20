package com.ucac.po;

import java.io.Serializable;

public class Work implements Serializable{
	private int id;
	private double documentScore;
	private double pictureScore;
	private int ranking;
	private double score;
	private int workState;
	private double videoScore;
	private Applicant applicant;
	public Applicant getApplicant() {
		return applicant;
	}
 
	public int getId() {
		return id;
	}
	public double getDocumentScore() {
		return documentScore;
	}
	public double getPictureScore() {
		return pictureScore;
	}
	public int getRanking() {
		return ranking;
	}
	public double getScore() {
		return score;
	}
	public int getWorkState() {
		return workState;
	}
	public double getVideoScore() {
		return videoScore;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDocumentScore(double documentScore) {
		this.documentScore = documentScore;
	}
	public void setPictureScore(double pictureScore) {
		this.pictureScore = pictureScore;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public void setWorkState(int workState) {
		this.workState = workState;
	}
	public void setVideoScore(double videoScore) {
		this.videoScore = videoScore;
	}
	
	@Override
	public String toString() {
		return "id:"+this.id+" |ranking:"+this.ranking+" |videoScore:"+this.videoScore+" |pictureScore:"+this.pictureScore+" |documentScore:"+this.documentScore+" |score:"+this.score;
	}
	
}
