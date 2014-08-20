package com.ucac.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import com.ucac.Applicant.service.ApplicantService;
import com.ucac.Applicant.service.impl.ApplicantServiceImpl;
import com.ucac.exception.DBException;
import com.ucac.exception.ErrorException;

public class TestApplicant {
	@Test
	public void testExcel() throws IOException, ErrorException, DBException{
		File  file  =new  File("D:/exmple.xls");
	    InputStream  stream  = new FileInputStream(file);
		HSSFWorkbook  book  = new HSSFWorkbook(stream);
		ApplicantService  service  = ApplicantServiceImpl.getInstance();
	    service.addExperts(book);
	    stream.close();
	}
}
