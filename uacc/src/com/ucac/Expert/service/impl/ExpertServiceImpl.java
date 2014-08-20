package com.ucac.Expert.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ucac.Expert.service.ExpertService;
import com.ucac.Work.service.impl.WorkServiceImpl;
import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.dao.impl.IndexDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Expert;
import com.ucac.po.ExpertAssess;
import com.ucac.util.MD5Util;
import com.ucac.vo.QueryResult;
import com.ucac.vo.WorkResult;
//liyixiang  chengjiang xionganping 
public class ExpertServiceImpl  implements ExpertService{
	//dao单例
		private   EntityDao entityDao = EntityDaoImpl.getInstance();
	
		private static class ExpertServiceHelper {
			static final ExpertService INSTANCE = new ExpertServiceImpl();
		}

		public static ExpertService getInstance() {
			return ExpertServiceHelper.INSTANCE;
		}
		@Override
	/**
	 * 
	 * 李轶翔
	 * @todo 		搜索单个专家信息（精确查询）
	 * @param expertName   专家姓名
	 * @return
	 * @returntype Expert
	 */
	public Expert searchSingleExpert(String expertName) throws ErrorException, DBException {
		// TODO Auto-generated method stub
		//用于对带有？的sql语句参数赋值
		 List<Object> parames = new ArrayList<Object>();
		 //参数赋值
		parames.add(expertName);
		 
		//实体类的参数变量名
		String sql_where = "expertName";
		
		//返回expert对象
		Expert expert = entityDao.findEntity(Expert.class, sql_where, parames);			
		
		return expert;
	}
	
	/**
	 * @throws DBException 
	 * @throws ErrorException 
	 * 
	 */
	
		@Override
		public String numofWorkResult(int expertId, int category,Boolean isFront) throws DBException {
			
			int count = 0;
			int finished = 0;
			String sql = "";
			int workState = 1;
			if(isFront == true) {
			sql = "select distinct c.applicant_id,c.expert_id,c.category,c.video_score,c.picture_score,c.document_score,c.score from " +
					"(select a.category,a.id applicant_id,b.expert_id,b.video_score,b.picture_score,b.document_score,b.score from t_applicant a " +
					"left outer join t_expert_assess b " +"on(a.id = b.applicant_id and expert_id = ?) )c,t_work d where c.applicant_id = d.id and category = ? and d.work_state = ? limit ?,?";
			}
			else {
				sql = "select t1.id applicant_id,t2.picture_score,t2.video_score,t2.document_score,t2.score,t1.community_name,t3.expert_name,t3.category from "+
						"(select community_name,id from t_applicant) t1 "+
						"inner join "+
						"(select work_state,id from t_work) t4 "+
						"on t1.id = t4.id "+
						"inner join "+
						"(select picture_score,video_score,document_score,score,applicant_id,expert_id from t_expert_assess) t2 "+
						"on t1.id = t2.applicant_id "+
						"inner join "+
						"(select expert_name,category,id from t_expert) t3 "+
						"on t2.expert_id = t3.id where t3.id=? and category = ? and t4.work_state = ? limit ?,?";

			}
			List<Object> parames = new ArrayList<Object>();
			parames.add(expertId);
			parames.add(category);
			parames.add(workState);
			List<WorkResult> workResults = entityDao.findAllEntityByCompose(WorkResult.class, 0, 10000, sql, parames);
			count = workResults.size();
			
			//计算finished
			for(int i=0;i<count;i++) {
				if(workResults.get(i).getScore() > 0.0)
					finished++;
			}
			
			return ""+count+","+finished;
		}
	
	
	
	
	@Override
	public ExpertAssess getExpertAssess(int applicantId, int expertId) throws ErrorException, DBException {
		ExpertAssess expertAssess = null;
		String sql_where = "applicant,expert";
		List<Object> parames=new ArrayList<Object>();
		parames.add(applicantId);
		parames.add(expertId);
			expertAssess = entityDao.findEntity(ExpertAssess.class, sql_where, parames);
			
		return expertAssess;
	}

	/**
	 * @throws DBException 
	 * 
	 */
	@Override
	public List<WorkResult> getWorksInfo(int expertId, int category,
			int nowpage, int pageSize ,Boolean isFront) throws DBException {
		List<WorkResult> workResults = new ArrayList<WorkResult>();
		String sql = "";
		if(isFront == true) {
		sql = "select distinct c.id,c.applicant_id,c.expert_id,c.category,c.video_score,c.picture_score,c.document_score,c.score from " +
				"(select a.category,a.id applicant_id,b.id,b.expert_id,b.video_score,b.picture_score,b.document_score,b.score from t_applicant a " +
				"left outer join t_expert_assess b " +"on(a.id = b.applicant_id and expert_id = ?) )c,t_work d where c.applicant_id = d.id and category = ? and d.work_state = ? limit ?,?";
		}
		else {
			sql = "select t1.id applicant_id,t2.picture_score,t2.video_score,t2.document_score,t2.score,t1.community_name,t3.expert_name,t3.category from "+
					"(select community_name,id from t_applicant) t1 "+
					"inner join "+
					"(select work_state,id from t_work) t4 "+
					"on t1.id = t4.id "+
					"inner join "+
					"(select picture_score,video_score,document_score,score,applicant_id,expert_id from t_expert_assess) t2 "+
					"on t1.id = t2.applicant_id "+
					"inner join "+
					"(select expert_name,category,id from t_expert) t3 "+
					"on t2.expert_id = t3.id where t3.id=? and category = ? and t4.work_state = ? limit ?,?";

		}
		List<Object> parames = new ArrayList<Object>();
		int workState = 1;//标志作品审查通过
		parames.add(expertId);
		parames.add(category);
		parames.add(workState);
		System.out.println(parames);
		workResults = entityDao.findAllEntityByCompose(WorkResult.class, (nowpage-1)*pageSize, pageSize, sql, parames);

		return workResults;
	}

	@Override
	public int modifyAccountState(Expert expert) throws ErrorException, DBException {
		Expert expert2 = null;
			 expert2 = entityDao.findById(Expert.class, expert.getId());
		if(expert2.getExpertState() == 1) {
			expert2.setExpertState(0);
		}else{
			expert2.setExpertState(1);
		}
		int i;
		try{
		i = entityDao.update(expert2);
		}catch (DBException e) {
			throw e;
		}catch (ErrorException e) {
			throw e;
		}
		return i;
	}
	
	
 
	/**
	 * 熊安平 TODO 批量的添加专家帐号
	 * 
	 * @param experts
	 *            是批量的专家帐号
	 * @return 返回给view 层的 专家帐号 实现思路: 拿到前台的传过来的数据,
	 *         先判断自己是否有电话邮件重复，再进行二次验证是否帐号重复,然后调用帐号来计算密码,最后入库
	 * @throws DBException
	 * @throws ErrorException
	 */
	@Override
	public QueryResult<Expert> addExpertInfo(List<Expert> experts)
			throws ErrorException, DBException {
		if (experts.size() == 0) {
			return null;
		}
		QueryResult<Expert> expertsDB = this.showExpertInfo(7, 1, 10000);
		experts = this.checkExpertUsernameLength(experts);
		// 清除邮箱和电话相同的帐号
		experts = this.checkAllExpertTelAndEmail(experts, experts);

		if (expertsDB != null) {
			experts = this.checkAllExpertTelAndEmail(experts,
					expertsDB.getResults());

			experts = this.checkAllExpertIsExists(experts,
					expertsDB.getResults());

		}

		System.out.println("-----------------需要保存的" + experts.size() + "条数据");
		// 设置初始密码
        //专家暂且没有加上md5 
		Iterator<Expert> expertIterator = experts.iterator();
		while (expertIterator.hasNext()) {
			Expert expert = expertIterator.next();

			expert.setPassword(MD5Util.EncoderByMd5(this
					.getExpertPasswordByExpertName(expert.getUsername())));

			EntityDaoImpl.getInstance().save(expert);
		    IndexDaoImpl.getInstance().save(expert);
		}

		// 开始保存专家 帐号
       
		 
		return EntityDaoImpl.getInstance().findAllEntity(Expert.class, 0, 15,
				null, null, null, 1);

	}

	/**
	 * 
	 * 熊安平 TODO ：拿到所有的专家帐号
	 * 
	 * @param category
	 *            专家帐号的分类 7 代表的是拿到全部
	 * @param page
	 *            拿到需要分页的数量
	 * @param maxSize
	 *            拿到一个页的最大数量
	 * @return 返回的值是QueryResult<Expert> 实现思路: 直接调用dao方法，密码统一用*****
	 * @throws DBException
	 *             数据库连接等处理异常
	 * @throws ErrorException
	 *             不是实体类哪些异常
	 */
	@Override
	public QueryResult<Expert> showExpertInfo(int category, int page,
			int maxSize) throws ErrorException, DBException {
		int firstIndex = (page - 1) * maxSize;
		QueryResult<Expert> experts = null;
		if (category == 7) {
			experts = EntityDaoImpl.getInstance().findAllEntity(Expert.class,
					firstIndex, maxSize, null, null, null, 0);
		} else {
			 
			String sql_where = "category";
			List<Object> parames = new ArrayList<Object>();
			parames.add(category);
			experts = EntityDaoImpl.getInstance().findAllEntity(Expert.class,
					firstIndex, maxSize, null, sql_where, parames, 0);
		}
		return experts;
	}

	/**
	 * 
	 * 熊安平 TODO 更新专家帐号
	 * 
	 * @param experts
	 *            专家帐号
	 * @return 返回的是更新的条目 实现思路: 首先在更新的时候判断专家的电话和电子邮箱是不是在库存中有相视的,是唯一的话,我就入库
	 *         判断的方式是先判断本experts list中的,在判断库存的。
	 * @throws DBException
	 * @throws ErrorException
	 */
	@Override
	public int modifyExpertInfo(List<Expert> experts) throws ErrorException,
			DBException {
		if (experts.size() == 0) {
			return 0;
		}

		QueryResult<Expert> expertsDB = this.showExpertInfo(7, 1, 10000);
		experts = this.checkExpertUsernameLength(experts);
		// 先判断是不是自己的电话和邮箱有重复,假如有三个是一样的,那么只留下最后那个
		experts = this.checkAllExpertTelAndEmail(experts, experts);

		if (expertsDB != null) {
			// 和数据库中的数据做比较
			experts = this.checkAllExpertTelAndEmail(experts,
					expertsDB.getResults());
		}
		System.out.println(experts.size() + "-------experts 的个数");
		// 开始存库
		int i = 0;
		Iterator<Expert> expertIteator = experts.iterator();
		while (expertIteator.hasNext()) {
			Expert expert = expertIteator.next();
			try {
				EntityDaoImpl.getInstance().update(expert);
				i++;
			} catch (DBException e) {
				System.out.println("保存" + expert.getUsername() + "时数据库连接出错");
				e.printStackTrace();
			} catch (ErrorException e) {
				System.out.println("保存" + expert.getUsername() + "时实体类出错");
				e.printStackTrace();
			}
		}
		return i;
	}

	/**
	 * 
	 * 熊安平 TODO 判断需要更新的专家帐号的名字的长度是不是 =4 ,不是的话不给添加进去
	 * 
	 * @return 返回的是帅选之后的结果 实现思路:
	 */
	private List<Expert> checkExpertUsernameLength(List<Expert> experts) {
		Iterator<Expert> expertsIterator = experts.iterator();
		while (expertsIterator.hasNext()) {
			Expert expert = expertsIterator.next();
			// if usename < 4
			if (expert.getUsername().length() != 4 && expert!=null) {
				experts.remove(expert);
				if(experts.size()!=0){
				expertsIterator = experts.iterator();
				}else{
					break;
				}
				}
		}
		return experts;

	}

	/**
	 * 
	 * 熊安平 TODO 核对专家帐号是否已经在数据库中存在了,在此过程中是需要自身验证的
	 * 
	 * @param experts
	 *            是需要添加的专家帐号
	 * @param expertsDB
	 *            是数据空已经存在的专家帐号
	 * @return 返回的是经过筛选出来的 experts数据 实现思路: 验证专家帐号是否存在
	 */
	private List<Expert> checkAllExpertIsExists(List<Expert> experts,
			List<Expert> expertsDB) {
		Iterator<Expert> expertsIterator = experts.iterator();
		while (expertsIterator.hasNext()) {
			Expert expert = expertsIterator.next();
			// 如果已经存在的话 我直接remove
			if (!this.checkOneExpertIsExists(expert, expertsDB)) {
				experts.remove(expert);
				expertsIterator = experts.iterator();
			}
		}
		return experts;
	}

	/**
	 * 
	 * 熊安平 TODO 用来单个的判断是否帐号存在
	 * 
	 * @param expert
	 *            需要被帅选的专家帐号
	 * @param expertsDB
	 *            拿到做帅选的模版的专家帐号
	 * @return flase表示是已经存在了,true表示的是为存在的 实现思路:
	 */
	private boolean checkOneExpertIsExists(Expert expert, List<Expert> expertsDB) {
		boolean flag = true;
		Iterator<Expert> expertDBItrator = expertsDB.iterator();
		while (expertDBItrator.hasNext()) {
			Expert expertDB = expertDBItrator.next();
			// 通过用户名来判断是否是相同的,而不是真正的名字,名字是可以重名的
			if (expertDB.getUsername().equals(expert.getUsername())) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * 
	 * 熊安平 TODO 用来 判断专家帐号的电话和邮件
	 * 
	 * @param experts
	 *            需要被判断的专家帐号
	 * @param expertsDB
	 *            需要用来做判断的专家帐号
	 * @return 返回的是已经删减了的专家帐号 实现思路:
	 */
	private List<Expert> checkAllExpertTelAndEmail(List<Expert> experts,
			List<Expert> expertsDB) {
		Iterator<Expert> expertsIterator = experts.iterator();
		while (expertsIterator.hasNext()) {
			Expert expert = expertsIterator.next();
			if (!this.checkOneExpertTelAndEmail(expert, expertsDB)) {
				experts.remove(expert);
			   if(experts.size()!=0){
				expertsIterator = experts.iterator();
			   }else{
				   break;
			   }
			  }
		}
		return experts;
	}

	/**
	 * 
	 * 熊安平 TODO 用来判断单个的专家帐号是否有重复
	 * 
	 * @param expert
	 * @param experts
	 * @return flase为 已经存在, true为不存在 实现思路:
	 */
	private boolean checkOneExpertTelAndEmail(Expert expert,
			List<Expert> experts) {
		boolean flag = true;
		Iterator<Expert> expertsIterator = experts.iterator();
		while (expertsIterator.hasNext()) {
			Expert expertDB = expertsIterator.next();
			// 通过判断电话和电子邮箱来判断是否有相同的 ,自己不加比较
			if(!expert.getUsername().equals(expertDB.getUsername())){
			  
			    if(expert.getExpertTel()!=null && expertDB.getExpertTel()!=null && expertDB.getExpertTel().equals(expert.getExpertTel())){
				flag=false;
				break;
			      }
			if(expert.getExpertEmail()!=null && expertDB.getExpertEmail()!=null&&   expertDB
					.getExpertEmail().equals(expert.getExpertEmail()) ){
				flag=false;
				break;
			 }
			} 
		}
		return flag;
	}

	/**
	 * 
	 * 熊安平 TODO 用来通过管理员的姓名来拿到密码
	 * 
	 * @param username
	 * @return 实现思路: 首先拿到那个字母,假如字母为多少的话,对应的就是多少, 数字为多少的话,就为多少
	 */
	private String getExpertPasswordByExpertName(String username) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < username.length(); i++) {
			if ((username.charAt(i) >= 'a' && username.charAt(i) <= 'z')
					|| (username.charAt(i) >= 'A' && username.charAt(i) <= 'Z')) {
				sb.append(this.getNumByCharacter(username.charAt(i)));
			} else {
				sb.append(this.getNumByNumber(username.charAt(i)));
			}
		}
		return sb.toString();
	}

	private String getNumByCharacter(char charater) {
		int value = charater;
		Double doubleValue = value / Math.PI;
		String a = doubleValue.toString();
 
		a = a.substring(a.lastIndexOf(".") + 1, a.lastIndexOf(".") + 4);
		return a;
	}

	private String getNumByNumber(char charater) {
		int value = charater;
		Double doubleValue = value / Math.PI;
		String a = doubleValue.toString();
		a = a.substring(a.lastIndexOf(".") + 5, a.lastIndexOf(".") + 6);
		return a;
	}

	/**
	 * 熊安平  
	 * TODO 用来在前台的搜索
	 * @return  返回的是lucenen中已经存取了好的数据
	 * 实现思路: 直接调用indexDao层  
	 */
	@Override
	public QueryResult<Expert> getExpertByLucene(String queryString,int firstIndex,int maxSize) {
	    QueryResult<Expert> queryResult  = null;
		
	    try {
			queryResult =  IndexDaoImpl.getInstance().findEntity(Expert.class, queryString, firstIndex, maxSize);
		   
	    } catch (IOException e) {
		 	e.printStackTrace();
		}
		 
	    return queryResult;
	}
	/**
	 * 
	 * 熊安平  
	 * TODO
	 * @param category
	 * @return
	 * @throws ErrorException
	 * @throws DBException
	 *需要导出的东西有帐号  密码 类别 姓名 电话 邮箱
	 */
	@Override
	public HSSFWorkbook exportHSSFbook(int category) throws ErrorException, DBException {
		 QueryResult<Expert> experts  = this.showExpertInfo(category, 1, 1000);
		 HSSFWorkbook  book  = null;
		 if(experts!=null){
			 //在此我们开始调用object  to HSsFWorkBook对象
			 book  = new HSSFWorkbook();
			 
			 HSSFSheet sheet = book.createSheet("专家帐号名单");
			 
			 HSSFRow   row   = sheet.createRow(0);
			 HSSFCell  cell1  =  row.createCell(0);
			    cell1.setCellValue("用户名");
			 HSSFCell  cell2  =  row.createCell(1);
			    cell2.setCellValue("密码");
			 HSSFCell  cell3  =  row.createCell(2);
			    cell3.setCellValue("类别");  
			 HSSFCell  cell4  =  row.createCell(3);
			    cell4.setCellValue("姓名");
			  HSSFCell  cell5  = row.createCell(4);
			    cell5.setCellValue("电话");
			  HSSFCell  cell6  = row.createCell(5);
			  cell6.setCellValue("邮箱"); 
			 
			  List<Expert> expertList   =  experts.getResults();
			  Iterator<Expert> expertIterator  = expertList.iterator();
			   int  i =1;
			   while(expertIterator.hasNext()){
				   Expert  expert   =expertIterator.next();
				   HSSFRow   row2  = sheet.createRow(i);
				   HSSFCell  cell1_1  =  row2.createCell(0);
				     cell1_1.setCellValue(expert.getUsername());
				   HSSFCell  cell2_2  =  row2.createCell(1);
				     cell2_2.setCellValue(this.getExpertPasswordByExpertName(expert.getUsername()));
				   HSSFCell  cell3_3  =  row2.createCell(2);
				     cell3_3.setCellValue(this.getCatrgory(expert.getCategory()));  
				   HSSFCell  cell4_4  =  row2.createCell(3);
				     cell4_4.setCellValue(expert.getExpertName());
				   HSSFCell  cell5_5  = row2.createCell(4);
				     cell5_5.setCellValue(expert.getExpertTel());
				   HSSFCell  cell6_6  = row2.createCell(5);
				     cell6_6.setCellValue(expert.getExpertEmail()); 
				   i++;
			   }
			    
		 }
		 
		 return book;
	}

	private String getCatrgory(int category){
		String string  =null;
		switch (category) {
		case 1:string="理论学习类";break;
		case 2:string="学术科技类";break;
		case 3:string="文艺体育类";break;
		case 4:string="社会公益类";break;
		case 5:string="实践创新类";break;
		case 6:string="其它类";break;
		}
		return string;
	}
	@Override
	public HSSFWorkbook exportExpertRecord(int expertId,int category) throws DBException {
		List<WorkResult>  result =     this.getWorksInfo(expertId, category, 1,1000, false);
		HSSFWorkbook  book  = null;
	 
		if(result!=null && result.size()!=0){
		 
			book  = new HSSFWorkbook();
			 HSSFSheet sheet = book.createSheet("专家打分记录名单");
			HSSFRow   row   = sheet.createRow(0);
			 HSSFCell  cell1  =  row.createCell(0);
			    cell1.setCellValue("专家姓名");
			 HSSFCell  cell2  =  row.createCell(1);
			    cell2.setCellValue("社团名称");
			 HSSFCell  cell3  =  row.createCell(2);
			    cell3.setCellValue("类别");  
			 HSSFCell  cell4  =  row.createCell(3);
			    cell4.setCellValue("视频得分");
			  HSSFCell  cell5  = row.createCell(4);
			    cell5.setCellValue("展板得分");
			  HSSFCell  cell6  = row.createCell(5);
			  cell6.setCellValue("材料得分"); 
			  
			  HSSFCell  cell7  = row.createCell(6);
			  cell7.setCellValue("最后得分"); 
			 
			Iterator<WorkResult> iterator  = result.iterator();
			int i=1;
			while(iterator.hasNext()){
				WorkResult  results  = iterator.next();
				HSSFRow   row1   = sheet.createRow(i);
				 HSSFCell  cell1_1  =  row1.createCell(0);
				 cell1_1.setCellValue(results.getExpertName());
				 HSSFCell  cell2_1  =  row1.createCell(1);
				 cell2_1.setCellValue(results.getCommunityName());
				 HSSFCell  cell3_1  =  row1.createCell(2);
				 cell3_1.setCellValue(this.getCatrgory(results.getCategory()));  
				 HSSFCell  cell4_1  =  row1.createCell(3);
				 cell4_1.setCellValue(results.getVideoScore());
				  HSSFCell  cell5_1  = row1.createCell(4);
				  cell5_1.setCellValue(results.getPictureScore());
				  HSSFCell  cell6_1  = row1.createCell(5);
				  cell6_1.setCellValue(results.getDocumentScore()); 
				 
				  HSSFCell  cell8_1  = row1.createCell(6);
				  cell8_1.setCellValue(results.getScore()); 
				  i++;
			}
		}
		return book;
	}
	@Override
	public HSSFWorkbook exportExcelWorkResult(int category,int sortType) throws DBException {
		QueryResult<WorkResult>  queryResult = WorkServiceImpl.getInstance().showWorkResult(
				1, 1000, category, sortType);
		 
		HSSFWorkbook  book  = null;
		if(queryResult!=null && queryResult.getResults()!=null &&queryResult.getResults().size()!=0){
			book  = new HSSFWorkbook();
			 HSSFSheet sheet = book.createSheet("比赛结果");
			 HSSFRow   row   = sheet.createRow(0);
			 HSSFCell  cell1  =  row.createCell(0);
			    cell1.setCellValue("学校");
			 HSSFCell  cell2  =  row.createCell(1);
			    cell2.setCellValue("社团名称");
			 HSSFCell  cell3  =  row.createCell(2);
			    cell3.setCellValue("社团类型");  
			 HSSFCell  cell4  =  row.createCell(3);
			    cell4.setCellValue("视频得分");
			  HSSFCell  cell5  = row.createCell(4);
			    cell5.setCellValue("展板得分");
			  HSSFCell  cell6  = row.createCell(5);
			  cell6.setCellValue("材料得分"); 
			   HSSFCell  cell7  = row.createCell(6);
			  cell7.setCellValue("最后得分"); 
			   HSSFCell  cell8  = row.createCell(7);
				  cell8.setCellValue("排名"); 
			
			  Iterator<WorkResult> iterator  = queryResult.getResults().iterator();
					int i=1;
					while(iterator.hasNext()){
						WorkResult  results  = iterator.next();
						HSSFRow   row1   = sheet.createRow(i);
						 HSSFCell  cell1_1  =  row1.createCell(0);
						 cell1_1.setCellValue(results.getSchool());
						 HSSFCell  cell2_1  =  row1.createCell(1);
						 cell2_1.setCellValue(results.getCommunityName());
						 HSSFCell  cell3_1  =  row1.createCell(2);
						 cell3_1.setCellValue(this.getCatrgory(results.getCategory()));  
						 HSSFCell  cell4_1  =  row1.createCell(3);
						 cell4_1.setCellValue(results.getVideoScore());
						  HSSFCell  cell5_1  = row1.createCell(4);
						  cell5_1.setCellValue(results.getPictureScore());
						  HSSFCell  cell6_1  = row1.createCell(5);
						  cell6_1.setCellValue(results.getDocumentScore()); 
						  HSSFCell  cell8_1  = row1.createCell(6);
						  cell8_1.setCellValue(results.getScore()); 
						  HSSFCell  cell7_1  = row1.createCell(7);
						  cell7_1.setCellValue(results.getRanking()); 
						  i++;
		     }  
				  
			
		}
		
		return book;
	}


}
