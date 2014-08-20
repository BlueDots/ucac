package com.ucac.vo;
/**
 * 作品的地址
 * @author Administrator
 *
 */
public class WorkUrl {
	private String applyFormUrl;
	private String documentUrl;
	private String pictureUrl1;
	private String pictureUrl2;
	private String videoUrl;
	public String getApplyFormUrl() {
		return applyFormUrl;
	}
	public String getDocumentUrl() {
		return documentUrl;
	}
	public String getPictureUrl1() {
		return pictureUrl1;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public String getPictureUrl2() {
		return pictureUrl2;
	}
	public void setPictureUrl2(String pictureUrl2) {
		this.pictureUrl2 = pictureUrl2;
	}
	public void setApplyFormUrl(String applyFormUrl) {
		this.applyFormUrl = applyFormUrl;
	}
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	public void setPictureUrl1(String pictureUrl1) {
		this.pictureUrl1 = pictureUrl1;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
}
