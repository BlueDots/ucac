package com.ucac.user.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.dao.impl.IndexDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Admin;
import com.ucac.po.Applicant;
import com.ucac.po.Expert;
import com.ucac.syw.exception.UploadException;
import com.ucac.syw.exception.UserException;
import com.ucac.syw.util.UploadUtils;
import com.ucac.user.servic.UserService;
import com.ucac.util.CheckTimeUtil;
import com.ucac.util.FileOperationUntil;
import com.ucac.vo.LoginInfo;
import com.ucac.vo.PasswordFindingInfo;
import com.ucac.vo.QueryResult;
import com.ucac.vo.RegisterInfo;
import com.ucac.vo.WorkUrl;

public class UserServiceImpl implements UserService {

	private EntityDao dao = EntityDaoImpl.getInstance();

	private static class UserServiceHelper {
		static final UserService INSTANCE = new UserServiceImpl();
	}

	public static UserService getInstance() {
		return UserServiceHelper.INSTANCE;
	}


	/**
	 * songyouwei
	 * 
	 * @todo 验证注册信息中的用户名/社团名是否已经存在，若存在则抛出UserException
	 * @param registerInfo
	 * @throws DBException
	 * @throws ErrorException
	 * @throws UserException
	 */
    @Override
	public void register(RegisterInfo registerInfo,String verificationCodeInSession) throws DBException,ErrorException,UserException {
		//检查RegisterInfo是否符合正则表达式，若符合则把RegisterInfo转为Applicant，否则返回null
    	Applicant applicant = UploadUtils.getApplicantFromRegisterInfo(registerInfo, verificationCodeInSession);
    	//验证用户名和密码
    	List params = new ArrayList<>();
    	params.add(applicant.getUsername());
    	Applicant daoApplicant = dao.findEntity(Applicant.class, "username", params);
    	if (daoApplicant == null || !daoApplicant.getPassword().equals(applicant.getPassword())) {
			throw new UserException(UserException.errorMsg_usernameorPassword);
		}
    	
    	//需要验证，在同学校同类型时，社团名不可以重复
    	String sql_where="category,school,communityName";
        List<Object> parames = new  ArrayList<Object>();
        parames.add(registerInfo.getCategory());
        parames.add(daoApplicant.getSchool());
        parames.add(registerInfo.getCommunityName());
        
        QueryResult<Applicant> queryResult = dao.findAllEntity(Applicant.class, 0, 10000, null, sql_where, parames, 0);
		
     	//若已存在，抛异常
    	if (queryResult != null && queryResult.getResults()!=null && queryResult.getResults().size()!=0) {
			throw new UserException("此学校此类别已存在此社团名！");
		}
		
		//防止更新密码
    	applicant.setPassword(null);
    	//设置ID
    	applicant.setId(daoApplicant.getId());
    	//改变状态
    	applicant.setStatus(1);
		
    	
		 
    	
//		verify(registerInfo);
    	//添加学校
    	applicant.setSchool(daoApplicant.getSchool());
		dao.update(applicant);
		IndexDaoImpl.getInstance().save(applicant);
	}
	
	/**
	 * songyouwei
	 * @todo 验证，在同学校同类型时，社团名不可以重复，若存在则抛出UserException
	 * @param registerInfo
	 * @throws DBException
	 * @throws ErrorException
	 * @throws UserException
	 */
	@Override
	public void verify(RegisterInfo registerInfo) throws DBException,ErrorException,UserException  {
		String username = registerInfo.getUsername();
		String communityName = registerInfo.getCommunityName();
		
		StringBuffer errorMsg = new StringBuffer();
		if (username != null) {
			List<Object> params = new ArrayList<Object>();
			params.add(username);
			int count = dao.getAllCount("select count(username) from t_applicant where username=?",params);
			if (count > 0) {
				errorMsg.append(UserException.errorMsg_username_exists);
			}
		}
		if (communityName != null) {
			List<Object> params = new ArrayList<Object>();
			params.add(communityName);
			int count = dao.getAllCount("select count(community_name) from t_applicant where community_name=?",params);
			if (count > 0) {
				errorMsg.append(UserException.errorMsg_communityName_exists);
			}
		}
		
		if (errorMsg.length() > 0) {
			throw new UserException(errorMsg.toString());
		}
	}
	
	
	
	
	//-----------------

	@Override
	public Object checkUserInfo(LoginInfo loginInfo,
			String verificationInSession) throws UserException, ErrorException,
			DBException { // 登录验证

		final int length; // 用户账户名长度
		length = loginInfo.getUsername().length();
		StringBuffer errorMsg = new StringBuffer();

		// 1.验证验证码是否正确
		String loginCode = loginInfo.getVerificationCode(); // 用户输入的验证码
		if (!loginCode.equals(verificationInSession)) {
		     // 验证码错误
			throw new UserException(UserException.errorMsg_verificationCode);
		}

		// 2.在数据库中查某类用户的账号
		List<Object> list = new ArrayList<>();
		list.add(loginInfo.getUsername());

		if (loginInfo.getAboveboard() == 1) { // 前台
			// a.专家

			if (length == 4) { // 专家账号4位
				if (CheckTimeUtil.checkAppraiseLogin() != true) {
					throw new UserException("专家登录尚未开放！");
				}
				Expert expert = new Expert();
				expert = dao.findEntity(Expert.class, "username", list);
				if (expert == null) { // 帐号不存在
					throw new UserException(UserException.errorMsg_usernameorPassword);
				}
				if (loginInfo.getPassword().equals(expert.getPassword())) {
					if (expert.getExpertState() == 0) {
						throw new UserException(UserException.errorMsg_expertNotFree);
					}
					return expert;
				} else {
					throw new UserException(UserException.errorMsg_usernameorPassword);
				}
			}
			// b.申报人员
			if (length >= 5 && length <= 12) { // 申报人员
				if (CheckTimeUtil.checkApplyLogin() != true) {
					throw new UserException("申报人员登录尚未开放！");
				}
				Applicant applicant = new Applicant();
				applicant = dao.findEntity(Applicant.class, "username", list);
				if (applicant == null) { // 帐号不存在
					throw new UserException(UserException.errorMsg_usernameorPassword);
				}
				
				if(applicant.getStatus()==0){
					throw new UserException(UserException.error_diedStatus);
				 }

				if (loginInfo.getPassword().equals(applicant.getPassword())) {
					return applicant;
				} else {
					throw new UserException(UserException.errorMsg_usernameorPassword);
				}

			}
		} else {
			if (loginInfo.getAboveboard() == -1) { // 后台了 后台就是管理员
				Admin admin = new Admin();
				admin = dao.findEntity(Admin.class, "username", list);
				if (admin == null) { // 帐号不存在
					throw new UserException(UserException.errorMsg_usernameorPassword);
				}
				if (loginInfo.getPassword().equals(admin.getPassword())) {
					return admin;
				} else {
					throw new UserException(UserException.errorMsg_usernameorPassword);
				}
			}
		}
		if (errorMsg.length() > 0) {
			throw new UserException(errorMsg.toString());
		}
		return null;

	}

	@Override
	public int getPassword(PasswordFindingInfo passwordInfo,
			String verificationInSession) throws UserException, ErrorException,
			DBException { // 找回密码之验证邮箱 返回-1：信息错误
		final int length; // 用户账户名长度
		length = passwordInfo.getUsername().length();
		StringBuffer exception = new StringBuffer();

		// 1.验证验证码是否正确
		String loginCode = passwordInfo.getVerificationCode(); // 用户输入的验证码
		if (!loginCode.equals(verificationInSession)) {
			throw new UserException(UserException.errorMsg_verificationCode);   // 验证码错误 
		}

		// 2.在数据库中专家或者申报人员的账号
		List<Object> list = new ArrayList<>();
		list.add(passwordInfo.getUsername());

		// a.专家
		if (length == 4) { // 专家账号4位
	//		if (CheckTimeUtil.checkAppraiseLogin() != true) {
		//		throw new UserException("专家登录尚未开放！");
	//		}
			Expert expert = new Expert();
			expert = dao.findEntity(Expert.class, "username", list);
			if (expert == null) { // 帐号不存在
				throw new UserException(UserException.errorMsg_usernameorPassword);
			}

			if (passwordInfo.getEmail().equals(expert.getExpertEmail())) {
				return expert.getId();
			} else {
				throw new UserException(UserException.errorMsg_emailFalse);
			}
		}
		// b.申报人员
		if (length >= 5 && length <= 12) { // 申报人员
		//	if (CheckTimeUtil.checkAppraiseLogin() != true) {
		//		throw new UserException("申报人员登录尚未开放！");
		//	}
			
			System.out.println("111111111111111111111111111111111");
			Applicant applicant = new Applicant();
			applicant = dao.findEntity(Applicant.class, "username", list);
			if (applicant == null) { // 帐号不存在
				throw new UserException(UserException.errorMsg_usernameFalse);
			}
			if (passwordInfo.getEmail().equals(applicant.getTeacherEmail())) {
				return applicant.getId();
			} else {
				throw new UserException(UserException.errorMsg_emailFalse);
			}
		}
		if (exception.length() > 0) {
			throw new UserException(exception.toString());
		}
		return -1;
	}

	@Override
	public boolean updatePassword(String username, String password)
			throws ErrorException, DBException, UserException {
		// TODO Auto-generated method stub

		List<Object> list = new ArrayList<>();

		if (username.length() >= 5 && username.length() <= 12) { // 申报人员改密码
			Applicant applicant = new Applicant();
			list.add(username);
			applicant = dao.findEntity(Applicant.class, "username", list); // 得到id，还有int型数据			
			if (applicant == null) {
				throw new UserException("操作错误，请重新申请发送邮件!");
			}
			Applicant upadateApplicant = new Applicant();
			upadateApplicant.setId(applicant.getId());
			upadateApplicant.setStatus(applicant.getStatus());
			upadateApplicant.setCategory(applicant.getCategory());
			upadateApplicant.setPassword(password);
			dao.update(upadateApplicant);
			return true;
		}
		if (username.length() == 4) { // 专家改密码
			Expert expert = new Expert();
			list.add(username);
			expert = dao.findEntity(Expert.class, "username", list);
			if (expert == null) {
				throw new UserException("操作错误，请重新申请发送邮件!");
			}
			Expert updateExpert = new Expert();
			updateExpert.setId(expert.getId());
		
			updateExpert.setCategory(expert.getCategory());
			updateExpert.setExpertState(expert.getExpertState());
			updateExpert.setPassword(password);
			dao.update(updateExpert);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteApplicant(String username) throws ErrorException,
			DBException { // 删除申报人员以及上传的文件
		// TODO Auto-generated method stub
 
		List<Object> list = new ArrayList<>();
		list.add(username);
		//int id;
		String path = null;
		try {
			path = URLDecoder.decode(UploadUtils.getWorksDirectory(), "UTF-8");
			System.out.print(path);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		WorkUrl workUrl = new WorkUrl();
		Applicant applicant  = dao.findEntity(Applicant.class, "username", list) ;
		dao.delete(Applicant.class, applicant.getId());
		
		workUrl.setApplyFormUrl(path+"/"+applicant.getId()+"-regForm.doc");
		workUrl.setDocumentUrl(path+"/"+applicant.getId()+"-material.doc");
		workUrl.setPictureUrl1(path+"/"+applicant.getId()+"-pic1.jpg");
		workUrl.setPictureUrl2(path+"/"+applicant.getId()+"-pic2.jpg");
		workUrl.setVideoUrl(path+"/"+applicant.getId()+"-video.flv");
		
		FileOperationUntil.deleteFile(workUrl);
		
		return false;
	}


	@Override
	public void upload(File[] files, Applicant applicant) throws DBException,
			ErrorException, UploadException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void upload(File file, int type, Applicant applicant)
			throws DBException, ErrorException, UploadException {
		// TODO Auto-generated method stub
		
	}


}
