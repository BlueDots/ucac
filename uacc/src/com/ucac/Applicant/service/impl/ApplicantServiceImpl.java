package com.ucac.Applicant.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.ucac.Applicant.service.ApplicantService;
import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.dao.impl.IndexDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Applicant;
import com.ucac.util.MD5Util;
import com.ucac.vo.QueryResult;
 
//liyixiang 
public class ApplicantServiceImpl implements ApplicantService {
	private ApplicantServiceImpl(){}
	//dao单例
		private   EntityDao entityDao = EntityDaoImpl.getInstance();
		private static class ApplicantServiceHelper {
			static final ApplicantService INSTANCE = new ApplicantServiceImpl();
		}

		public static ApplicantService getInstance() {
			return ApplicantServiceHelper.INSTANCE;
		}
	@Override
	/**
	 * 
	 * 李轶翔
	 * @todo     搜索申报人员
	 * @param applyName  参数为申报人员账号
	 * @return
	 * @returntype Applicant
	 */
	public Applicant showApplyInfo(String applyName) throws ErrorException, DBException {
		// TODO Auto-generated method stub
		//用于对带有？的sql语句参数赋值
		 List<Object> parames = new ArrayList<Object>();
		 //参数赋值
		parames.add(applyName);
		 
		//实体类的参数变量名
		String sql_where = "username";
		
		//返回expert对象
		Applicant applicant = entityDao.findEntity(Applicant.class, sql_where, parames);			
		
		return applicant;
		
	}
	@Override
	public QueryResult<Applicant> getExpertByLucene(String queryString,
			int firstIndex, int maxSize) {
		    QueryResult<Applicant> queryResult  = null;
			
		    try {
				queryResult =  IndexDaoImpl.getInstance().findEntity(Applicant.class, queryString, firstIndex, maxSize);
			   
		    } catch (IOException e) {
			 	e.printStackTrace();
			}
			 
		    return queryResult;
	}
	/**
	 * 
	 * 熊安平    
	 * TODO
	 * @param excel
	 * @return
	 *  先判断电子表格合不合法,在将电子表格生成专家帐号,然后批量的导入
	 *  1  代表电子表格合法 否则0 代表是不和法
	 * @throws DBException 
	 * @throws ErrorException 
	 */
	@Override
	public int addExperts(HSSFWorkbook excel) throws ErrorException, DBException {
		int result=1;
		if(checkExcelIsRight(excel)){
			//生成帐号和密码准备入库
			List<Applicant> applicants = this.makeAllApplicant(excel);
			if(applicants.size()!=0){
				entityDao.BatchSave(applicants, Applicant.class);
			}else
				result=0;
		}else{
			result= 0;
		}
		return result;
	}
	/**
	 * 
	 * 熊安平  
	 * TODO 将电子表格中的数据转换为applicant
	 * @param excel
	 * @return
	 * @throws DBException 
	 * @throws ErrorException 
	 *
	 */
	private List<Applicant> makeAllApplicant (HSSFWorkbook excel) throws ErrorException, DBException{
		QueryResult<Applicant> applicantDB = this.getAllApplicants();
		List<Applicant> applicants  = new ArrayList<Applicant>();
		HSSFSheet  sheet  = excel.getSheetAt(0);
		Iterator<Row>  rowIterator  = sheet.rowIterator();
		int sum = 0 ;
		while(rowIterator.hasNext()){
			Row row  = rowIterator.next(); 
			Cell  firstCell  = row.getCell(0);
	        Cell  secndCell  = row.getCell(1);
	 	    Cell  thirdCell  = row.getCell(2);
	 	    Cell  fourCell   = row.getCell(3);
	        sum=sum+1;
 		    if(sum==300 || firstCell.getStringCellValue().equals("")){
  		    	break;
  		    }
 		   if(firstCell.getCellType()==1 &&  firstCell.getStringCellValue().equals("序号")){
		       continue;
		    } 
 		    int num  = Integer.parseInt(fourCell.getStringCellValue())+1;
 		    for(int i=1;i<num;i++){
 		    Applicant  applicant  = new Applicant();
 		    applicant.setSchool(secndCell.getStringCellValue());
 		    applicant.setUsername(thirdCell.getStringCellValue()+""+i);
 		    String password = this.getPassByApplicantName(thirdCell.getStringCellValue()+""+i);
 		    password=MD5Util.EncoderByMd5(password);
 		    applicant.setPassword(password);
 		    applicant.setStatus(0);
 		    if(checkApplicantIsInDB(applicant,applicantDB)){
 		      applicants.add(applicant);
 		      
 		    } 
 		   }
 		}
		return  applicants;
	}
	/**
	 * 
	 * 熊安平  
	 * TODO 判断电子表格是不是合法的
	 * @param excel
	 * @return  返回true  false
	 *  先判断是不是序号的值有为NULL的后者不为String Num的  ，在是判断 校名是不是大于等于4  小于等于12 最后是判断是不是社团数字
	 *  先判断是不是有相同姓名的,在判断是不是有填写了我需要的学校名,学校短名(是不是大于4)是否填写了要生成的数量
	 */
	private  boolean checkExcelIsRight(HSSFWorkbook excel){
		boolean   result  = true;
		HSSFSheet sheet =  excel.getSheetAt(0);
	 
		result  = checkExcleRowDataIsRight(sheet);  //核对表格的数据是否合法
	    
	    if(result){
	    	result=checkExcleDataIsRepeat(sheet);  //核对数据是不是重复了
	    } 
	    return result;
	}
	/**
	 * 
	 * 熊安平  
	 * TODO判断表格中的数据 比如学校是不是重复了,  学校的简称是不是重复了  
	 * @param sheet
	 * @return
	 *
	 */
	private boolean checkExcleDataIsRepeat(HSSFSheet sheet){
		boolean  result  = true;
 
		Iterator<Row>  rowIterator  = sheet.rowIterator();
		int sum = 0 ;
		while(rowIterator.hasNext()){
			Row row  = rowIterator.next(); 
			Cell  firstCell  = row.getCell(0);
	 
	 		sum=sum+1;
 		    if(sum==300 || firstCell.getStringCellValue().equals("")){
  		    	break;
  		    }
 		    
 		   result=checkExcelDataRowByRow(row,sheet);
 		    
 		   if(!result){
 			   break;
 		   }
		}
		return result;
	}
	
	private boolean checkExcelDataRowByRow(Row row,HSSFSheet sheet){
		boolean result = true;
		int sum = 0 ;
		Cell  secondCell = row.getCell(1);
 		Cell  thirdCell  = row.getCell(2);
 		Iterator<Row>  rowIterator  = sheet.rowIterator();
		while(rowIterator.hasNext()){
			Row row2  = rowIterator.next(); 
			Cell  firstCell2  = row2.getCell(0);
			Cell  secondCell2 = row2.getCell(1);
	 		Cell  thirdCell2  = row2.getCell(2);
	 		sum=sum+1;
 		    if(sum==300 || firstCell2.getStringCellValue().equals("")){
  		    	break;
  		    }
 		     
 		    if(row.getRowNum()!=row2.getRowNum()){
 		    	if(secondCell.getStringCellValue().equals(secondCell2.getStringCellValue()) || thirdCell.getStringCellValue().equals(thirdCell2.getStringCellValue())){
 		    		result=false;
 		    		break;
 		    	}
 		    }
 		}
		
		return result;
	}
	/**
	 * 
	 * 熊安平  
	 * TODO  判断表格中的数据是不是合法的
	 * @param sheet
	 * @return
	 *
	 */
	private boolean checkExcleRowDataIsRight(HSSFSheet sheet){
		boolean  result  = true;
		Iterator<Row> rowIterator = sheet.rowIterator();
		int sum  =0 ;
     	while(rowIterator.hasNext()){
     	    
     		Row row  = rowIterator.next(); 
 		    Cell  firstCell  = row.getCell(0);
 		    Cell  secondCell = row.getCell(1);
 		    Cell  thirdCell  = row.getCell(2);
 		    Cell  fourCell   = row.getCell(3); 
 		    
 		    sum=sum+1;
 		    if(sum==300 || firstCell.getStringCellValue().equals("")){
  		    	break;
  		    }
 		    
 		    
 		    if(firstCell.getCellType()==1 &&  firstCell.getStringCellValue().equals("序号")){
		       continue;
		    } 
		    
		    if(checkNumIsRight(firstCell) && checkSchoolName(secondCell) && checkScoolShortName(thirdCell) && checkCommunityNum(fourCell) ){
	        }else{
	            result=false;
		    	break;
		    }
		    
 		  
 	    }
     	return  result;
	}
	
	private boolean  checkNumIsRight(Cell firstCell){
		Boolean result = true;
		if(firstCell.getCellType()==1){
			 try {
				Integer.parseInt(firstCell.getStringCellValue());
			} catch (NumberFormatException e) {
				 e.printStackTrace();
				 result=false;
			}
		}else{
			    result=false;
		}
		return result;
	}
	
	private boolean  checkSchoolName(Cell secondCell){
		Boolean result = true;
		if(secondCell.getCellType()==1 && !secondCell.getStringCellValue().equals("")){
		 
		 }else{
			    result=false;
		}
		return result;
	}
	
	private boolean  checkScoolShortName(Cell cell){
		Boolean result = true;
		if(cell.getCellType()==1 && !cell.getStringCellValue().equals("") && (cell.getStringCellValue().length()>=4 ||cell.getStringCellValue().length()<=9)){
			
		}else{
			    result=false;
		}
		return result;
	}
	private boolean  checkCommunityNum(Cell cell){
		Boolean result = true;
		if(cell.getCellType()==1 ){
			 try {
				int  num =Integer.parseInt(cell.getStringCellValue());
			    if(num>50)
			    	 result=false;
			 } catch (NumberFormatException e) {
					 e.printStackTrace();
					 result=false;
				} 
		}else{
		    result=false;
		}
		return result;
	}
	
	private  String  getPassByApplicantName(String  username){
		StringBuffer sb = new StringBuffer();
		String first = username.substring(0, 2);
		
		
		sb.append(first);
		sb.append(getNumByCharacter(username.charAt(2)+username.charAt(1)+username.charAt(username.length()-1)));
		sb.append(getStringByCharacter(username.charAt(username.length()-1) +username.charAt(0)+username.charAt(1)) );
		return sb.toString();
	}
	private String getNumByCharacter(int i) {
		int value = i;
		Double doubleValue = value / Math.PI;
		String a = doubleValue.toString();
	 
		a = a.substring(a.lastIndexOf(".") + 1, a.lastIndexOf(".") + 4);
		return a;
	}
	private String getStringByCharacter(int i) {
		int value = i;
		Double doubleValue = (value*5+10) / Math.PI;
		String a = doubleValue.toString();
	 
		a = a.substring(a.lastIndexOf(".") + 1, a.lastIndexOf(".") + 4);
		return a;
	}
	@Override
	public QueryResult<Applicant> getAllApplicants() throws ErrorException, DBException {
		return entityDao.findAllEntity(Applicant.class, 0, 1000, null, null, null, 0); 
	}
	
	private  boolean checkApplicantIsInDB(Applicant applicant,QueryResult<Applicant> applicants){
		boolean result = true;
		if(applicants==null){
			result =true;
		}else{
			Iterator<Applicant> applicantIterator  = applicants.getResults().iterator();
			while(applicantIterator.hasNext()){
				Applicant applicant1  = applicantIterator.next();
				if(applicant.getUsername().equals(applicant1.getUsername())){
					result=false;
					break;
				}
			}
		} 
		return result;
	}
	@Override
	public HSSFWorkbook getExcelByApplicant() throws ErrorException, DBException {
		 QueryResult<Applicant> applicants  = this.getAllApplicants();
		 HSSFWorkbook  book  = null;
		 if(applicants!=null){
			 //在此我们开始调用object  to HSsFWorkBook对象
			 book  = new HSSFWorkbook();
			 
			 HSSFSheet sheet = book.createSheet("申报人员帐号名单");
			 
			 HSSFRow   row   = sheet.createRow(0);
			 HSSFCell  cell1  =  row.createCell(0);
			    cell1.setCellValue("用户名");
			 HSSFCell  cell2  =  row.createCell(1);
			    cell2.setCellValue("密码");
			 HSSFCell  cell3  =  row.createCell(2);
			    cell3.setCellValue("学校");  
			 
			 
			  List<Applicant> applicantList   =  applicants.getResults();
			  Iterator<Applicant> applicantIterator  = applicantList.iterator();
			   int  i =1;
			   while(applicantIterator.hasNext()){
				   Applicant  applicant   =applicantIterator.next();
				   HSSFRow   row2  = sheet.createRow(i);
				   HSSFCell  cell1_1  =  row2.createCell(0);
				     cell1_1.setCellValue(applicant.getUsername());
				   HSSFCell  cell2_2  =  row2.createCell(1);
				     cell2_2.setCellValue(this.getPassByApplicantName(applicant.getUsername()));
				   HSSFCell  cell3_3  =  row2.createCell(2);
				     cell3_3.setCellValue(applicant.getSchool());  
			 
				   i++;
			   }
			    
		 }
		 
		 return book;
	} 
	 

}
