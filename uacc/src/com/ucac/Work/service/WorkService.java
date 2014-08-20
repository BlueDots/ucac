package com.ucac.Work.service;

import java.util.List;

import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Applicant;
import com.ucac.vo.QueryResult;
import com.ucac.vo.WorkInfo;
import com.ucac.vo.WorkResult;
import com.ucac.vo.WorkUrl;
//chengjiang
public interface WorkService {
	/**
	 * 
	 * 李轶翔
	 * @todo 						比赛结果展示
	 * @param page 		当前为第几页
	 * @param maxSize	每页的数量
	 * @param category 	类别 1-7  7为全部
	 * @param sortType	排序方式，0降序，1升序
	 * @return	
	 * @throws DBException 
	 * @returntype QueryResult<WorkResult>
	 * 									QueryResult为自定义容器
	 * 									WorkResult为VO,用于指定容器的类型
	 */

	public QueryResult<WorkResult> showWorkResult(int page,int maxSize,int category,int sortType) throws DBException;

	/**
	 * 
	 * 李轶翔
	 * @todo 比赛结果的搜索
	 * @param schoolName 学校名称
	 * @param communityName  社团名称
	 * @return
	 * @returntype QueryResult<WorkResult>
	 * 									QueryResult为自定义容器
	 * 									WorkResult为VO,用于指定容器的类型	 
	 */
	public QueryResult<WorkResult> searchWorkResult(String schoolName,String communityName) throws ErrorException, DBException;
	/**
	 * 
	 * Administrator
	 * @todo 查看作品详细信息：【游客，申报人员，管理员，专家】(陈江)
	 * //WorkUrlVo对象(包括DocumentURL,VideoURL,PictureURL属性): 
	 * 返回作品的URL
	 * @param id
	 * @return
	 * @returntype WorkUrl
	 */
	public WorkUrl findSpecificWorkById(String id);
	
	
	/**
	 * 
	 * 陈江
	 * @todo 审核状态【null为未提交状态.-1是刚提交状态 0.未通过审核；1.通过审核】
	 * @param applicants
	 * @param buttonId
	 * @return
	 * @throws DBException 
	 * @throws ErrorException 
	 * @returntype int
	 */
	public int checkWorkContent(List<Applicant> applicants,String assessType) throws ErrorException, DBException;
	
	
	/**
	 * 陈江
	 * 显示作品的总数
	 * admin_assessWork.jsp
	 * @param id
	 * @return
	 * @throws DBException 
	 */
	
	public int numofWorkInfo()throws DBException;
	
	
	/**
	 * 陈江
	 * 显示作品信息
	 * admin_assessWork.jsp
	 * @param id
	 * @return
	 * @throws DBException 
	 */
	public QueryResult<WorkInfo> showWorkInfos(int nowpage,int maxSize) throws DBException;
}
