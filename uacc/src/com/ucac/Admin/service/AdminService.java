package com.ucac.Admin.service;


import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.exception.IOException;
import com.ucac.lhq.exception.FileConsistencyException;
import com.ucac.lhq.exception.FileNameIllegalException;
import com.ucac.lhq.exception.FileNotExistException;
import com.ucac.lhq.exception.PathIllegalException;
import com.ucac.po.Settings;
import com.ucac.yiwei.exception.JudgementException;

//yiwei   lihuiqiang 
public interface AdminService {
	/**
	 * 
	 * laihuiqiang
	 * @todo 备份文件
	 * @param value 需要备份文件的类型
	 * @param path 备份的路径
	 * @return
	 * @throws IOException
	 * @throws PathIllegalException
	 * @throws FileNotExistException
	 * @throws Exception 
	 * @returntype int
	 */
	public int backup( int value,String path) throws IOException,PathIllegalException,FileNotExistException, Exception;
	/**
	 * 
	 * laihuiqiang
	 * @todo 恢复文件
	 * @param value 需要备份文件的类型
	 * @param fileName 恢复文件的路径以及文件名(只能为.zip文件格式)
	 * @return
	 * @throws IOException
	 * @throws FileNameIllegalException
	 * @throws FileConsistencyException
	 * @returntype int
	 */
	public int restore( int value,String fileName) throws IOException,FileNameIllegalException,FileConsistencyException;

	/**
	 * 
	 * yiwei
	 * @todo :更新settings表
	 * @param setting:封装好的setting对象
	 * @param numForm:表单的id
	 * @return 1表示更新成功, 0表示更新失败
	 * @throws ErrorException
	 * @throws DBException
	 * @throws JudgementException
	 * @returntype int
	 */
	public int modifySettingContent(Settings setting,int numForm) throws ErrorException, DBException, JudgementException;
	
	/**
	 * 
	 * yiwei
	 * @todo :获取数据库中的setting表的记录
	 * @return:settings对象,null无记录返回
	 * @throws ErrorException
	 * @throws DBException
	 * @returntype Settings
	 */
	public Settings showSettingContent() throws ErrorException, DBException;
}
 