package com.ucac.user.servic;

import java.io.File;

import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Applicant;
import com.ucac.syw.exception.UploadException;
import com.ucac.syw.exception.UserException;
import com.ucac.vo.LoginInfo;
import com.ucac.vo.PasswordFindingInfo;
import com.ucac.vo.RegisterInfo;

public interface UserService {

	/**
	 *陈振清
	 *TODO
	 *@param args
	 * @throws DBException 
	 * @throws ErrorException 
	 */
	public  Object checkUserInfo(LoginInfo loginInfo,String verificationInSession) throws UserException, ErrorException, DBException;      //验证登录信息
	public int getPassword(PasswordFindingInfo passwordInfo,String VerificationInSession)throws UserException, ErrorException, DBException;  //找回密码
	public boolean updatePassword(String username,String password) throws ErrorException, DBException, UserException;     //更改密码
	public boolean deleteApplicant(String username) throws ErrorException, DBException;            //删除申报人员
	public	void	register(RegisterInfo registerInfo,String verificationCodeInSession) throws DBException,ErrorException,UserException ;
	public void verify(RegisterInfo registerInfo) throws DBException,ErrorException,UserException  ;
	public void upload(File[] files,Applicant applicant) throws DBException,ErrorException,UploadException ;
	public void upload(File file, int type, Applicant applicant) throws DBException,ErrorException,UploadException ;
}
