package com.ucac.test;

import org.junit.Test;

import com.ucac.dao.IndexDao;
import com.ucac.dao.impl.IndexDaoImpl;
import com.ucac.po.Applicant;
import com.ucac.po.Expert;
import com.ucac.util.ReadXmlUtil;
import com.ucac.vo.WorkResult;

public class TestReadXmlUtil {
	@Test
	public void testEntity() {
		ReadXmlUtil readXmlUtil;
		System.out.println(ReadXmlUtil.checkClassIsExists(WorkResult.class));
	}
	
	
	@Test
	public void testIndexDao(){
		IndexDao index =  IndexDaoImpl.getInstance();
		Expert expert  = new Expert();
	    expert.setId(1);
		expert.setExpertName("小李子");
		expert.setUsername("小李子");
		index.save(expert);
	}
	@Test
	public   void  testIndexDaoAddApplication(){
		IndexDao index  = IndexDaoImpl.getInstance();
		Applicant applicant  = new Applicant();
		applicant.setId(1);
		applicant.setCommunityName("e02");
		applicant.setSchool("江西师大");
		index.save(applicant);
	}
}
