package com.ucac.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ucac.vo.Page;

public class TestPage {
	public static void main(String args[]) {
      Page  page  =  new Page();
      page.setPage(1);
      page.setCount(100);
      page.setMaxSize(15);
     System.out.println(page.getLinkNum());
 	String  a  = null;
 	String b  = "1212";
 	System.out.println(b.equals(a));
    List<String> string  = new ArrayList<>();
    string.iterator();
 	
 	Iterator<Integer>  iterator  = page.getLinkNum().iterator();
     
     while(iterator.hasNext()){
    	 if(1==1){
    		 System.out.println(iterator.next());
    		 break;
    	 }
     }
	}
}
