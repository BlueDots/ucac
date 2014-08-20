package com.ucac.dao;

import java.io.IOException;

import com.ucac.vo.QueryResult;

public interface IndexDao {
	/**
	 * 
	 * 熊安平
	 * TODO  用来保存索引库中的数据
	 * @param object 需要传入的数据
	 * 实现思路: 这是通过 注解老保存的 
	 *        在该项目总需要保存的数据有这些 ：
	 *        Applicant  
	 *           ： id 
	 *           : username
	 *           : community_name
	 *           : school
	 *        Expert:
	 *           :  id
	 *           :  username
	 *           
	 *    保存的过程中是需要将object先进行 自定义注解的判断，然后再去转化为document对象,保存的       
	 *             
	 */
	 
	 
	public void save(Object object);
	public <T>  void delete(Class<T> t,Object id);
	public <T> QueryResult<T> findEntity(Class<T> t,String queryString,int firstIndex,int maxSize) throws IOException;
}