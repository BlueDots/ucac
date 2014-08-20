package com.ucac.syw.exception;

public class UploadException extends Exception {
	
	public static String errorMsg_lengh_short = "上传的文件数目不足！";
	
	public static String errorMsg_doc = "报名表或申报材料必须是doc文件！";
	public static String errorMsg_flv = "视频必须是flv文件！";
	public static String errorMsg_jpg = "展板必须是jpg文件！";
	
	public static String errorMsg_io = "服务器存储异常！";
	public static String errorMsg_other = "文件上传不合要求！";

	public UploadException(String message) {
		super(message);
	}
	
}
