package com.ucac.Work.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ucac.Work.service.WorkService;
import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Applicant;
import com.ucac.po.Work;
import com.ucac.syw.util.UploadUtils;
import com.ucac.vo.QueryResult;
import com.ucac.vo.WorkInfo;
import com.ucac.vo.WorkResult;
import com.ucac.vo.WorkUrl;
//chengjiang 
public class WorkServiceImpl  implements WorkService{
	private WorkServiceImpl(){}
	private   EntityDao entityDao = EntityDaoImpl.getInstance();
	private static class WorkServiceHelp {
		static final WorkService INSTANCE = new WorkServiceImpl();
	}

	public static WorkService getInstance() {
		return WorkServiceHelp.INSTANCE;
	}
	@Override
	/**
	 * 
	 * 李轶翔
	 * @todo 						比赛结果展示
	 * @param page 		当前为第几页
	 * @param maxSize	每页的数量
	 * @param category 	类别 1-7  7为全部
	 * @param sortType	排序方式，0降序，1升序, -1为游客查看界面调用（为降序）
	 * @return	
	 * @returntype QueryResult<WorkResult>
	 * 									QueryResult为自定义容器
	 * 									WorkResult为VO,用于指定容器的类型
	 */
	public QueryResult<WorkResult> showWorkResult(int page, int maxSize,
			int category, int sortType) throws DBException {
		/**
		 * 该方法调用Entitydao的findAllEntityByCompose方法
		 */
		//用于存放dao方法的返回值
		 List<WorkResult> list = new ArrayList<WorkResult>();
		 //作为此service方法的返回值
		 QueryResult<WorkResult> query = new QueryResult<WorkResult>();
		//用于对带有？的sql语句参数赋值
		 List<Object> parames = new ArrayList<Object>();		
		 //sql语句初始化
		 String sql = "";

		 String sqlAll = "";
		//判断是否为全部查询		
		if(category==7){
				
		//排序选择
			
				
				if(sortType==1){
					//后台比赛结果查询界面1  升序
					sql = "select a.id applicant_id,a.community_name,a.school,a.category,w.picture_score,w.video_score,w.document_score,w.score,w.ranking from t_applicant a,t_work w where a.id=w.id and w.work_state=1 order by w.ranking asc limit ? , ?;";
					
				}else{
					//后台比赛结果查询界面0  降序
					//前台比赛结果查询界面-1 降序
					sql = "select a.id applicant_id,a.community_name,a.school,a.category,w.picture_score,w.video_score,w.document_score,w.score,w.ranking from t_applicant a,t_work w where a.id=w.id and w.work_state=1 order by w.ranking desc  limit ? , ?;";
				
				}
				//调用dao的方法
				
					try {
						
						list = entityDao.findAllEntityByCompose(WorkResult.class,(page-1)*maxSize,maxSize, sql,null);
					
						//查询一共有多少条数据
						sqlAll ="select count(*) from t_applicant a,t_work w where a.id=w.id and w.work_state=1; ";
						
						
					} catch (DBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				
				
				
		}else{
			
				//带有？的赋值
				 parames.add(category);
				
				 //sql语句的赋值
				 
				 if(sortType==1){
						//后台比赛结果查询界面1  升序
						sql = "select a.id applicant_id,a.community_name,a.school,a.category,w.picture_score,w.video_score,w.document_score,w.score,w.ranking from t_applicant a,t_work w where a.id=w.id and  category=? and w.work_state=1 order by w.ranking asc limit ? , ?;";
						
					}else{
						//后台比赛结果查询界面0  降序
						//前台比赛结果查询界面-1 降序
						sql = "select a.id applicant_id,a.community_name,a.school,a.category,w.picture_score,w.video_score,w.document_score,w.score,w.ranking from t_applicant a,t_work w where a.id=w.id and  category=? and w.work_state=1 order by w.ranking desc  limit ? , ?;";
					
					}
				
					
				list = entityDao.findAllEntityByCompose(WorkResult.class,(page-1)*maxSize,maxSize, sql,parames);		
				
				//查询一共有多少条数据
				sqlAll ="select count(*) from t_applicant a,t_work w where a.id=w.id and category=? and w.work_state=1; ";
		
		}	
				
		int size = entityDao.getAllCount(sqlAll, parames);
		
		//对于返回值的赋值处理
		query.setResults(list);
		
		//数据库中一共有多少条数据	
		query.setTotalCount(size);
		
		return query;
	}

	/**
	 * 
	 * 李轶翔
	 * @todo 比赛结果的搜索
	 * @param schoolName 学校名称
	 * @param communityName  社团名称
	 * @return
	 * @throws DBException 
	 * @throws ErrorException 
	 * @returntype QueryResult<WorkResult>
	 * 									QueryResult为自定义容器
	 * 									WorkResult为VO,用于指定容器的类型	 
	 */
	@Override
	public QueryResult<WorkResult> searchWorkResult(String schoolName,
			String communityName) throws ErrorException, DBException {
		// TODO Auto-generated method stub
		//用于存放dao方法的返回值
		 List<WorkResult> list = new ArrayList<WorkResult>();
				
		//用于对带有？的sql语句参数赋值
		List<Object> parames = new ArrayList<Object>(); 
		
		//返回值初始化
		QueryResult<WorkResult> query = new QueryResult<WorkResult>();
		
		// where条件的传入参数
		String sql = "";
		
		//搜索结果分页赋值
		int page = 1;
		int maxSize = 500;
		
		//判断传入的搜索条件
		
		if(schoolName==""&&communityName==""){
			//学校名称和社团名称都为空
			 sql = "select a.id applicant_id,a.community_name,a.school,a.category,w.picture_score,w.video_score,w.document_score,w.score,w.ranking from t_applicant a,t_work w where a.id=w.id and w.work_state=1 and school like \"%\"?\"%\" and community_name like \"%\"?\"%\" limit ? , ?;";
			 parames.add("狗屎+狗屎=狗屎");
			 parames.add("狗屎+狗屎=狗屎");
			 list = entityDao.findAllEntityByCompose(WorkResult.class,(page-1)*maxSize,maxSize, sql,parames);			 
			
		}else if(schoolName==""&&communityName!=""){
			//只根据社团名称查询
			sql = "select a.id applicant_id,a.community_name,a.school,a.category,w.picture_score,w.video_score,w.document_score,w.score,w.ranking from t_applicant a,t_work w where a.id=w.id and w.work_state=1 and  community_name like \"%\"?\"%\" limit ? , ?;";
			parames.add(communityName);
			list = entityDao.findAllEntityByCompose(WorkResult.class,(page-1)*maxSize,maxSize, sql,parames);
			
		}else if(communityName==""&&schoolName!=""){
			//只根据学校名称查询
			sql = "select a.id applicant_id,a.community_name,a.school,a.category,w.picture_score,w.video_score,w.document_score,w.score,w.ranking from t_applicant a,t_work w where a.id=w.id and w.work_state=1 and school like \"%\"?\"%\" limit ? , ?;";
			parames.add(schoolName);
			list = entityDao.findAllEntityByCompose(WorkResult.class,(page-1)*maxSize,maxSize, sql,parames);
			
		} else {
			//根据学校名称和社团名称查询
			 sql = "select a.id applicant_id,a.community_name,a.school,a.category,w.picture_score,w.video_score,w.document_score,w.score,w.ranking from t_applicant a,t_work w where a.id=w.id and w.work_state=1 and school like \"%\"?\"%\" and community_name like \"%\"?\"%\" limit ? , ?;";
			 parames.add(schoolName);
			 parames.add(communityName);				 
			 list = entityDao.findAllEntityByCompose(WorkResult.class,(page-1)*maxSize,maxSize, sql,parames);			 
				 
		}
		
		//对于返回值的赋值处理
		query.setResults(list);
				
		//设置用于分页时一共有多少页
		
		query.setTotalCount((list.size()/maxSize)+1);			
		
		return query;
	}


	@Override
	public WorkUrl findSpecificWorkById(String id) {
		WorkUrl wUrl = new WorkUrl();
		String path = UploadUtils.getWorksDirectory();
		wUrl.setVideoUrl(path+id+"-video.flv");
		wUrl.setDocumentUrl(path+id+"-material.doc");
		wUrl.setPictureUrl1(path+id+"-pic1.jpg");
		wUrl.setPictureUrl2(path+id+"-pic2.jpg");
		wUrl.setApplyFormUrl(path+id+"-regForm.doc");
		return wUrl;
	}

	@Override
	public int checkWorkContent(List<Applicant> applicants, String assessType) throws ErrorException, DBException {
		int count = 0;
		Work work = new Work();
		if(assessType.equals("assess")) {
			
			for(int i=0;i<applicants.size();i++) {
				work.setId(applicants.get(i).getId());
				work.setWorkState(1);
				entityDao.update(work);
				count++;
			}
		}else if(assessType.equals("notAssess")){
			for(int i=0;i<applicants.size();i++) {
				work.setId(applicants.get(i).getId());
				work.setWorkState(0);
				entityDao.update(work);
				count++;
			}
		}
		
		return count;
	}
	
	@Override
	public QueryResult<WorkInfo> showWorkInfos(int nowpage,int maxSize) throws DBException {
		QueryResult<WorkInfo> wResult = new QueryResult<WorkInfo>();
		List<WorkInfo> workInfos = null;
		String sql = "select a.id,community_name,school,a.category,work_state from t_applicant a,t_work b where a.id = b.id limit ?,?";
		workInfos = entityDao.findAllEntityByCompose(WorkInfo.class, (nowpage-1)*maxSize, maxSize, sql, null);
		wResult.setResults(workInfos);
		wResult.setTotalCount(numofWorkInfo());
		return wResult;
	}

	
	@Override
	public int numofWorkInfo() throws DBException {
		List<WorkInfo> workInfos = new ArrayList<WorkInfo>();
		String sql = "select a.id,community_name,school,a.category,work_state from t_applicant a,t_work b where a.id = b.id limit ?,?";
		workInfos = entityDao.findAllEntityByCompose(WorkInfo.class, 0, 10000, sql, null);
		return workInfos.size();
	}

	

 
}
