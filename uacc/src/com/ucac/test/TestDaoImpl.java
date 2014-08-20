package com.ucac.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ucac.dao.EntityDao;
import com.ucac.dao.impl.EntityDaoImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;
import com.ucac.po.Applicant;

public class TestDaoImpl {
	@Test
	public void testDao() throws ErrorException, DBException{
		EntityDao  dao  = EntityDaoImpl.getInstance();
		List<Applicant> applicants   = new ArrayList<Applicant>();
	    for  (int i=0 ;i<10;i++){
	    	Applicant applicant   = new Applicant();
	    	applicant.setCategory(1);
			applicant.setCommunityName("nihao");
			applicant.setPassword("1212");
			applicant.setSchool("212");
			applicant.setStatus(0);
			applicant.setTeacherEmail("21212");
			applicant.setTeacherPhone("212");
			applicant.setUsername("1212"+i);
			applicant.setTeacherName("121");
			applicants.add(applicant);
		 }
		dao.BatchSave(applicants, Applicant.class);
	     
      }
}
