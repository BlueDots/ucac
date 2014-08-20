package com.ucac.syw.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.UUID;

import net.sf.jmimemagic.Magic;

import org.apache.commons.fileupload.FileItem;

import com.ucac.po.Applicant;
import com.ucac.syw.exception.UploadException;
import com.ucac.syw.exception.UserException;
import com.ucac.util.MD5Util;
import com.ucac.vo.RegisterInfo;

public class UploadUtils {
	
	/**
	 * songyouwei
	 * @todo 获取作品目录【../WebRoot/Works/】，若不存在则创建
	 * @return
	 */
	public static String getWorksDirectory() {
		//获取classes目录
		String pathString = ImgCompresser.class.getClassLoader().getResource("").getPath().substring(1);
		for (int i = 0; i < 3; i++) {
			int lastIndex = pathString.lastIndexOf("/");
			pathString = pathString.substring(0, lastIndex);
		}
		pathString = pathString+"/Works/";
		//若不存在则创建
		File dir = new File(pathString);
		if (!dir.exists()) {
			dir.mkdir();
		}
		if("Linux".equals(System.getProperties().getProperty("os.name"))){
           pathString="/"+pathString;
		}
		return pathString;
	}
	
	/**
	 * songyouwei
	 * @todo  检查RegisterInfo是否符合正则表达式，若符合则把RegisterInfo转为Applicant，否则返回null
	 * @param registerInfo
	 * @param verificationCodeInSession
	 * @return
	 * @throws UserException
	 * @returntype Applicant
	 */
	public static Applicant getApplicantFromRegisterInfo(RegisterInfo registerInfo,String verificationCodeInSession) throws UserException {
		
		String nullRegex = "^\\s*$";
		String usernameRegex = "^[a-zA-Z]\\w{4,11}$";
		String passwordRegex = "^[a-zA-Z]\\w{5,11}$";
		String phoneRegex = "^(1(([35][0-9])|(47)|[8][01236789]))\\d{8}$";
		String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		StringBuffer exceptionMessage = new StringBuffer();
		if (registerInfo.getVerificationCode().matches(nullRegex) || !registerInfo.getVerificationCode().equals(verificationCodeInSession)) {
			exceptionMessage.append(UserException.errorMsg_verificationCode);
		}
		if (registerInfo.getUsername().matches(nullRegex) ||!registerInfo.getUsername().matches(usernameRegex)) {
			exceptionMessage.append(UserException.errorMsg_username);
		}
		if (registerInfo.getPassword().matches(nullRegex) ||!registerInfo.getPassword().matches(passwordRegex)) {
			exceptionMessage.append(UserException.errorMsg_password);
		}
		if (registerInfo.getPassword2().matches(nullRegex) ||!registerInfo.getPassword2().equals(registerInfo.getPassword())) {
			exceptionMessage.append(UserException.errorMsg_password2);
		}
		if (registerInfo.getCommunityName().matches(nullRegex)) {
			exceptionMessage.append(UserException.errorMsg_communityName);
		}
		if (registerInfo.getTeacherName().matches(nullRegex)) {
			exceptionMessage.append(UserException.errorMsg_username);
		}
		if (registerInfo.getTeacherPhone().matches(nullRegex) ||!registerInfo.getTeacherPhone().matches(phoneRegex)) {
			exceptionMessage.append(UserException.errorMsg_phone);
		}
		if (registerInfo.getTeacherEmail().matches(nullRegex) ||!registerInfo.getTeacherEmail().matches(emailRegex)) {
			exceptionMessage.append(UserException.errorMsg_email);
		}
		
		if (exceptionMessage.length() != 0) {
			throw new UserException(exceptionMessage.toString());
		}
		
		Applicant applicant = new Applicant();
		applicant.setUsername(registerInfo.getUsername());
		applicant.setPassword(MD5Util.EncoderByMd5(registerInfo.getPassword()));
		applicant.setCommunityName(registerInfo.getCommunityName());
		applicant.setCategory(registerInfo.getCategory());
		applicant.setTeacherName(registerInfo.getTeacherName());
		applicant.setTeacherPhone(registerInfo.getTeacherPhone());
		applicant.setTeacherEmail(registerInfo.getTeacherEmail());
		
		return applicant;
	}
	
	/**
	 * songyouwei
	 * @todo 验证文件类型是否符合要求
	 * @param file
	 * @throws UploadException
	 */
	private static void verifyFile(FileItem fileItem,String fileMimeType) throws UploadException {
		
		StringBuffer errorMsg = new StringBuffer();
		
		String fieldName = fileItem.getFieldName();
		System.out.println(fieldName);
		if (fieldName.equals("regform") || fieldName.equals("material")) {
			// 报名表或申报材料
			if (!fileMimeType.equals("application/msword")) {
				errorMsg.append(UploadException.errorMsg_doc);
			}
		} else if (fieldName.equals("video")) {
			// 视频的mime type暂空,用后缀判断
			if (!fileItem.getName().substring(fileItem.getName().lastIndexOf('.')+1).equals("flv")) {
				errorMsg.append(UploadException.errorMsg_flv);
			}
		} else if (fieldName.equals("pic1") || fieldName.equals("pic2")) {
			// 展板
			if (!fileMimeType.equals("image/jpeg")) {
				errorMsg.append(UploadException.errorMsg_jpg);
			}
		} else {
			errorMsg.append(UploadException.errorMsg_other);
		}
		// 如果有异常信息则抛出异常
		if (errorMsg.length() > 0) {
			throw new UploadException(errorMsg.toString());
		}
	}
	
	/**
	* @Author: songyouwei
	* @Title: getFileItemMimeType 
	* @Description 针对插件无法获取mimetype的处理，有异常则返回""
	* @param fileItem
	* @return 
	 */
	private static String getFileItemMimeType(FileItem fileItem) {
		try {
			String tempFileName = UUID.randomUUID().toString();
			File file = new File(getWorksDirectory()+tempFileName);
			fileItem.write(file);
			String mimeType = Magic.getMagicMatch(file, false, false).getMimeType().toLowerCase();
			file.delete();
			return mimeType;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	* @Author: songyouwei
	* @Title: verifyFile 
	* @Description 使用插件上传的得到的文件，检测的mime都是otc-stream类型，上面的方法作废
	* @param fileItem
	* @throws UploadException
	 */
//	public static void verifyFile(FileItem fileItem) throws UploadException {
//		StringBuffer errorMsg = new StringBuffer();
//		
//		String fieldName = fileItem.getFieldName();
//		String fileExt = fileItem.getName().substring(fileItem.getName().lastIndexOf('.')+1);
//		
//		if (fieldName.equals("regform") || fieldName.equals("material")) {
//			// 报名表或申报材料
//			if (!fileExt.equals("doc")) {
//				errorMsg.append(UploadException.errorMsg_doc);
//			}
//		} else if (fieldName.equals("video")) {
//			// 视频
//			if (!fileExt.equals("flv")) {
//				errorMsg.append(UploadException.errorMsg_flv);
//			}
//		} else if (fieldName.equals("pic1") || fieldName.equals("pic2")) {
//			// 展板
//			if (!fileExt.equals("jpg")) {
//				errorMsg.append(UploadException.errorMsg_jpg);
//			}
//		}
//		// 如果有异常信息则抛出异常
//		if (errorMsg.length() > 0) {
//			throw new UploadException(errorMsg.toString());
//		}
//	}
	
	/**
	 * songyouwei
	 * @todo 存储文件组
	 * @param files 要上传的文件
	 * @param dirPath 目录地址
	 * @param applicant 申请人
	 * @exception UploadException
	 */
//	public static void saveFiles(File[] files,String dirPath,Applicant applicant) throws UploadException {
//		for (int i = 0; i < files.length; i++) {
//			saveFile(files[i], i, dirPath, applicant);
//		}
//	}
	
	/**
	 * songyouwei
	 * @todo 根据类型存储单个文件
	 * @param file
	 * @param type
	 * @param dirPath
	 * @param applicant
	 * @throws UploadException
	 */
//	public static void saveFile(File file, int type, String dirPath,Applicant applicant) throws UploadException {
//		int id = applicant.getId();
//		try {
//			switch (type) {
//			case 0:
//				//报名表
//				FileUtils.copyFileToDirectory(file, new File(dirPath+"doc/"+id+"-regform.doc"));
//				break;
//			case 1:
//				//视频
//				FileUtils.copyFileToDirectory(file, new File(dirPath+"flv/"+id+"-video.flv"));
//				break;
//			case 2:
//				//展板1
//				FileUtils.copyFileToDirectory(file, new File(dirPath+"pic/"+id+"-pic1.jpg"));
//				break;
//			case 3:
//				//展板2
//				FileUtils.copyFileToDirectory(file, new File(dirPath+"pic/"+id+"-pic2.jpg"));
//				break;
//			case 4:
//				//申报材料
//				FileUtils.copyFileToDirectory(file, new File(dirPath+"doc/"+id+"-material.doc"));
//				break;
//			}
//		} catch (IOException e) {
//			throw new UploadException(UploadException.errorMsg_io);
//		}
//	}
	
	/**
	* @Author: songyouwei
	* @Title: verifyAndSaveFile 
	* @Description 验证文件类型，并存储到 ../Works/{applicantId}-fieldName.{fileExt}
	* @param fileItem
	* @param applicantId
	* @throws UploadException
	* @return void
	 */
	public static void verifyAndSaveFile(FileItem fileItem,int applicantId) throws UploadException {
		String workDirPath = UploadUtils.getWorksDirectory();
		System.out.println(workDirPath+"-----------------");
		String fileName = +applicantId+"-"+fileItem.getFieldName()+fileItem.getName().substring(fileItem.getName().lastIndexOf('.'));
		//防止大小写问题
		fileName = fileName.toLowerCase();
		
			//写入硬盘，获取mimeType，可能有io异常
			String mimeType = null;
			System.out.println("---------mime------");
			File file = new File(getWorksDirectory()+fileName);
			 
			try {
				//先写入硬盘
				fileItem.write(file);
				
				
				//如果是flv视频，获取mime会有问题，所以不判断
				if (fileItem.getFieldName().equals("video")) {
					return;
				}
				
				
				//获取mime
				mimeType = Magic.getMagicMatch(file, false, false).getMimeType().toLowerCase();
				 
			} catch (Exception e) {
				e.printStackTrace();
				// TODO Auto-generated catch block
				throw new UploadException(UploadException.errorMsg_io);
			}
			
			//验证上传的文件mimeType是否合要求，有异常先删除文件再抛异常
			try {
				verifyFile(fileItem,mimeType);
			} catch (Exception e) {
				file.delete();
				throw e;
			}
			
			//如果是申报材料，要做转换处理
			if (fileItem.getFieldName().equals("material")) {
				//转换申报doc材料为HTML文档，并存储
				Doc2HtmlUtil.convertAndSave(workDirPath, applicantId+"-material.doc", applicantId+"-material.html");
				//压缩Html文档中过大的图片文件
				ImgCompresser.compressDir(workDirPath+applicantId+"-material");
				//解决乱码问题【把gb2312编码改为utf-8】
				Doc2HtmlUtil.changeCharSet(workDirPath+applicantId+"-material.html");
			}
			
			//如果是报名表
			if (fileItem.getFieldName().equals("regform")) {
				//转换申报doc材料为HTML文档，并存储
				Doc2HtmlUtil.convertAndSave(workDirPath, applicantId+"-regform.doc", applicantId+"-regform.html");
				//压缩Html文档中过大的图片文件
				ImgCompresser.compressDir(workDirPath+applicantId+"-regform");
				//解决乱码问题【把gb2312编码改为utf-8】
				Doc2HtmlUtil.changeCharSet(workDirPath+applicantId+"-regform.html");
			}
	}
	
	/**
	* @Author: songyouwei
	* @Title: AllFilesUploaded 
	* @Description 判断指定申请人是否所有文件都已经上传过了【5个上传文件+2个HTML文件】
	* @param applicantId
	* @return
	 */
	public static boolean  AllFilesUploaded(final int applicantId) {
		
		File dir = new File(getWorksDirectory());
		String[] fileNames = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.startsWith(applicantId+"-") && name.contains(".");
			}
		});
		return  fileNames.length==7?true:false;
	}
	
	/**
	* @Author: songyouwei
	* @Title: getFileUploadStateByFieldName 
	* @Description 根据fieldName和applicantId获取文件的上传状态
	* @param applicantId,fieldName 
	* @return
	 */
	public static boolean getFileUploadStateByFieldName(int applicantId, String fieldName) {
		boolean ifExists = false;
		switch (fieldName) {
		case "regForm":
			ifExists = new File(getWorksDirectory()+applicantId+"-regform.doc").exists();
			break;
		case "video":
			ifExists = new File(getWorksDirectory()+applicantId+"-video.flv").exists();
			break;
		case "pic1":
			ifExists = new File(getWorksDirectory()+applicantId+"-pic1.jpg").exists();
			break;
		case "pic2":
			ifExists = new File(getWorksDirectory()+applicantId+"-pic2.jpg").exists();
			break;
		case "material":
			ifExists = new File(getWorksDirectory()+applicantId+"-material.html").exists();
			break;
		}
		
		System.out.println(ifExists+"---------是否存在");
		return ifExists;
		
	}
}
