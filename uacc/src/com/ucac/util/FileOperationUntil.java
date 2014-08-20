package com.ucac.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.ucac.lhq.exception.FileConsistencyException;
import com.ucac.lhq.exception.FileNameIllegalException;
import com.ucac.lhq.exception.FileNotExistException;
import com.ucac.lhq.exception.PathIllegalException;
import com.ucac.vo.WorkUrl;

public class FileOperationUntil {

	@Test
	/**
	 * 
	 * laihuiqiang
	 * @todo  备份上传文件
	 * @param originalFileName 上传文件的默认路径
	 * @param zipFilePath 备份的路径
	 * @return
	 * @returntype int 0表示失败，1表示成功
	 */
	public static int backupFile(String originalFileName,String zipFilePath) throws PathIllegalException, com.ucac.exception.IOException, Exception{
		return backupHelper(originalFileName,zipFilePath);//调用方法实现zipFile;
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo  备份数据库文件
	 * @param originalFileName 数据库文件的默认路径
	 * @param zipFilePath 备份的路径
	 * @return
	 * @throws com.ucac.exception.IOException 
	 * @throws PathIllegalException 
	 * @returntype int 0表示失败，1表示成功
	 */
	/**
	public static int backupDB(String originalFileName,String zipFilePath) throws PathIllegalException, com.ucac.exception.IOException,Exception{
		return backupHelper(originalFileName,zipFilePath);//调用方法实现zipFile;
	}*/
	 public static int backupDB(OutputStream output, String dbname,String mysqlBinPath,String username,String password) {  
     	//String test = "D:/mysql/mysql 5.1/bin/mysqldump -uroot -pruanjian11 ucac";
         String command = mysqlBinPath + "mysqldump -u" + username  
                 + " -p" + password +" "+dbname;  
         PrintWriter p = null;  
         BufferedReader reader = null;  
         try {  
             p = new PrintWriter(new OutputStreamWriter(output, "utf8"));  
             Process process = Runtime.getRuntime().exec(command);  
             InputStreamReader inputStreamReader = new InputStreamReader(process  
                     .getInputStream(), "utf8");  
             reader = new BufferedReader(inputStreamReader);  
             String line = null;  
             while ((line = reader.readLine()) != null) {  
                 p.println(line);  
             }  
             p.flush();  
             WriteLog.writeLog("admin", "备份数据库成功!");
             return 1;
         } catch (UnsupportedEncodingException e) { 
        	 WriteLog.writeLog("admin", "备份数据库失败!");
             return 0; 
         } catch (IOException e) {  
        	 WriteLog.writeLog("admin", "文件读写异常，备份数据库失败!");
        	 return 0;  
         } finally {  
             try {  
                 if (reader != null) {  
                     reader.close();  
                 }  
                 if (p != null) {  
                     p.close();  
                 }  
             } catch (IOException e) {  
            	 WriteLog.writeLog("admin", "文件读写异常，备份数据库失败!");
            	 return 0;   
             }  
         }  
     }  
	 public static int backupDB(String dest, String dbname,String mysqlBinPath,String username,String password) {  
         try {  
        	 String baseDir = dest.substring(0, dest.lastIndexOf("/"));
        	 System.out.print("this is already come into backup method");
        	 System.out.print(baseDir);
        	 if (baseDir.length() > 3) {
				File dirFile = new File(baseDir);
				dirFile.mkdirs();
			}
             OutputStream out = new FileOutputStream(dest); 
             return backupDB(out, dbname,mysqlBinPath,username,password);  
         } catch (FileNotFoundException e) {  
             return 0;  
         }  
     }  
	/**
	 * 
	 * laihuiqiang
	 * @todo  备份上传文件和数据库文件
	 * @param originalFileName 所有文件的默认路径
	 * @param zipFilePath 备份的路径
	 * @return
	 * @throws com.ucac.exception.IOException 
	 * @throws PathIllegalException 
	 * @returntype int 0表示失败，1表示成功
	 */
	public static int backupAll(String originalFileName,String zipFilePath,String dest, String dbname,String mysqlBinPath,String username,String password) throws PathIllegalException, com.ucac.exception.IOException,Exception{
		int flag =0; 
		String[] path = originalFileName.split("_");
		if (backupDB(dest,dbname,mysqlBinPath,username,password)==1&&backupFile(path[0],zipFilePath)==1) {
			flag = 1;
			WriteLog.writeLog("admin", "备份全部成功!");
		}
		return flag;//调用方法实现zipFile;
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo 恢复上传文件
	 * @param zipFileName 需要恢复的上传的文件名
	 * @param targetBaseDirName 默认恢复的路径
	 * @param orginalFileName 原始的文件名，用于对文件名进行检测
	 * @return
	 * @throws FileNameIllegalException 
	 * @throws com.ucac.exception.IOException 
	 * @throws FileConsistencyException 
	 * @returntype int 0表示失败，1表示成功,2 恢复的文件名与原文件名不一致
	 */
    public static int restoreFile(String zipFileName,String targetBaseDirName,String orginalFileName) throws FileNameIllegalException, com.ucac.exception.IOException, FileConsistencyException{
    	targetBaseDirName = targetBaseDirName.substring(0, targetBaseDirName.length()-1);
		int lastIndex = targetBaseDirName.lastIndexOf("/");//最后一个/的位置
		targetBaseDirName = targetBaseDirName.substring(0, lastIndex+1);//原始文件的路径
		System.out.print(targetBaseDirName);
    	return restoreHelper(zipFileName, targetBaseDirName, orginalFileName);
    }
    /**
	 * 
	 * laihuiqiang
	 * @todo 恢复数据库文件
	 * @param zipFileName 需要恢复的数据库的文件名
	 * @param targetBaseDirName 默认恢复的路径
	 * @param orginalFileName 原始的文件名，用于对文件名进行检测
	 * @return
     * @throws FileNameIllegalException 
     * @throws com.ucac.exception.IOException 
     * @throws FileConsistencyException 
	 * @returntype int 0表示失败，1表示成功,2 恢复的文件名与原文件名不一致
	 */
    /**
    public static int restoreDB(String zipFileName,String targetBaseDirName,String orginalFileName) throws FileNameIllegalException, com.ucac.exception.IOException, FileConsistencyException{
    	return restoreHelper(zipFileName, targetBaseDirName, orginalFileName);
    }*/
    public static int restoreDB(String zipFileName,String dest,String dbName,String mysqlBinPath,String username,String password) {     
        try {   
            //String fPath = "c:/ucac.sql"; 
        	int lastIndex = zipFileName.lastIndexOf(File.separator);
    		int pomitIndex = zipFileName.indexOf(".");
    		String formation = zipFileName.substring(pomitIndex + 1, zipFileName.length());
    		if (!formation.equals("sql")) {
    			WriteLog.writeLog("admin", "数据库文件格式不正确，恢复失败!");
    			throw new FileConsistencyException("必须是sql格式的文件");
    		}else{
        	 Runtime rt = Runtime.getRuntime();     
             
             // 调用 mysql 的 cmd:     
             Process child = rt.exec(mysqlBinPath+"mysql.exe -u"+username+" -p"+password+" "+dbName);     
             OutputStream out = child.getOutputStream();//控制台的输入信息作为输出流     
             String inStr;     
             StringBuffer sb = new StringBuffer("");     
             String outStr;     
             BufferedReader br = new BufferedReader(new InputStreamReader(     
                     new FileInputStream(dest), "utf8"));     
             while ((inStr = br.readLine()) != null) {     
                 sb.append(inStr + "\r\n");     
             }     
             outStr = sb.toString();     
     
             OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");     
             writer.write(outStr);     
             // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免     
             writer.flush();     
             // 别忘记关闭输入输出流     
             out.close();     
             br.close();     
             writer.close();     
             WriteLog.writeLog("admin", "恢复数据库成功!");
             System.out.println("/* Load OK! */");  
             return 1;
    		}
     
         } catch (Exception e) {     
        	 WriteLog.writeLog("admin", "恢复数据库失败!");
             return 0;
         }     
    }    
    /**
	 * 
	 * laihuiqiang
	 * @todo 恢复全部文件
	 * @param zipFileName 需要恢复全部文件的文件名
	 * @param targetBaseDirName 默认恢复的路径
	 * @param orginalFileName 原始的文件名，用于对文件名进行检测
	 * @return
     * @throws FileNameIllegalException 
     * @throws com.ucac.exception.IOException 
     * @throws FileConsistencyException 
	 * @returntype int 0表示失败，1表示成功,2 恢复的文件名与原文件名不一致
	 */
    public static int restoreAll(String zipFileName,String targetBaseDirName,String orginalFileName,String dest,String dbName,String mysqlBinPath,String username,String password) throws FileNameIllegalException, com.ucac.exception.IOException, FileConsistencyException{
    	int flag = 1;
    	flag = restoreDB(zipFileName,dest, dbName, mysqlBinPath, username, password);
    	flag = restoreFile(zipFileName, targetBaseDirName, orginalFileName);
    	if (flag == 1) {
    		WriteLog.writeLog("admin", "恢复全部成功!");
		}
    	return flag;
    }
    
    public static int backupHelper(String originalFileName,String zipFilePath) throws PathIllegalException, com.ucac.exception.IOException, IOException{
	
    	int flag = 1;
    	originalFileName = originalFileName.substring(0, originalFileName.length()-1);
		int lastIndex = originalFileName.lastIndexOf("/");//最后一个/的位置
		String baseDirFile = originalFileName.substring(0, lastIndex+1);//原始文件的路径
		System.out.print(baseDirFile);
		String fileName = originalFileName.substring(lastIndex+1, originalFileName.length());//需要zip的原始文件的文件名
		System.out.print("\n"+fileName);
        if (!zipFilePath.endsWith("/")) {
        	flag = 0;
        	WriteLog.writeLog("admin", "路径不合法，备份失败!");
			throw new PathIllegalException("路径不合法!");
		}else{
			String toFileName = zipFilePath + fileName + ".zip";//需要存放zip文件的地址以及拼凑的备份文件的文件名
	        flag = zipFile(baseDirFile, fileName, toFileName);
		}
        //System.out.print(zipFilePath + fileName);
		return  flag;

	}
    /**
     * 
     * laihuiqiang
     * @todo 对路径参数进行
     * @param zipFileName 
     * @param targetBaseDirName
     * @param orginalFileName
     * @return
     * @throws FileNameIllegalException 
     * @throws com.ucac.exception.IOException 
     * @throws FileConsistencyException 
     * @returntype int
     */
	public static int restoreHelper(String zipFileName,String targetBaseDirName,String orginalFileName) throws FileNameIllegalException, com.ucac.exception.IOException, FileConsistencyException{
		
		int flag = 0;
		int lastIndex = zipFileName.lastIndexOf(File.separator);
		int pomitIndex = zipFileName.indexOf(".");
		String fileName = zipFileName.substring(lastIndex + 1, pomitIndex);
		String formation = zipFileName.substring(pomitIndex + 1, zipFileName.length());
		
		if (!formation.equals("zip")) {
			WriteLog.writeLog("admin", "文件格式不正确，恢复失败!");
			throw new FileConsistencyException("必须是zip格式的文件");
		}
		if (!(fileName.equals(orginalFileName))) {
			WriteLog.writeLog("admin", "文件名不一致，恢复失败!");
			throw new FileNameIllegalException("文件名不合法，您的文件名可能修改过或者选择类型与文件不一致");
		}else{
			flag = upZipFile(zipFileName, targetBaseDirName);
			return flag;
		}
	}
    /**
     * 
     * laihuiqiang
     * @ToDo 删除指定人员的上传文件
     * @param workUrl 封住的文件的路径对象
     * @return 0表示失败，1表示成功
     * @returntype int 0表示失败，1表示成功
     */
    public static int deleteFile(WorkUrl workUrl){
    	int flag = 0;
    	flag = deleteSingleFile(workUrl.getDocumentUrl());
    	flag = deleteSingleFile(workUrl.getPictureUrl1());
    	flag = deleteSingleFile(workUrl.getPictureUrl2());
    	flag = deleteSingleFile(workUrl.getVideoUrl());
    	flag = deleteSingleFile(workUrl.getApplyFormUrl());
    	if (flag == 1) {
    		WriteLog.writeLog("admin", "删除物理文件成功!");
		}else {
			WriteLog.writeLog("admin", "删除物理文件失败!");
		}
    	return flag;
    }
    /**
     * 
     * laihuiqiang
     * @todo 删除单个文件
     * @param SingleFilePath 单个文件的地址
     * @return o表示删除失败，1表示成功
     * @returntype int 0表示失败，1表示成功
     */
    private static int deleteSingleFile(String SingleFilePath){//删除单个文件
		int flag = 0;
		File file =  new File(SingleFilePath);
		if (file.exists()&&file.isFile()) {
			file.delete();
			flag = 1;
		}
		return flag;
	}
    /**
     * 
     * laihuiqiang
     * @todo 删除文件夹下的多个文件
     * @param DirectoryPath 文件夹得绝对路径
     * @return 0表示失败，1表示成功
     * @returntype int 0表示失败，1表示成功
     */
	private static int deleteAllFiles(String DirectoryPath){//删除多个文件
	    int flag = 0;
		File file = new File(DirectoryPath);
		File files[] = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			flag = 0;
			flag = deleteSingleFile(files[i].getAbsolutePath());
		}
		return flag;
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo 备份文件主要逻辑方法
	 * @param baseDirFile 备份文件的根目录
	 * @param fileName 备份文件的文件名
	 * @param toFileName 备份文件的路径
	 * @return
	 * @throws com.ucac.exception.IOException 
	 * @returntype int 0表示失败，1表示成功
	 */
	private static int zipFile(String baseDirFile,String fileName,String toFileName) throws com.ucac.exception.IOException, IOException{//压缩文件
		int flag = 1;
		
		if (baseDirFile == null) {
			flag = 0;
			return flag;
		}
		
		File baseDir = new File(baseDirFile);
		if (!baseDir.isDirectory()||(!baseDir.exists())) {
			WriteLog.writeLog("admin", "文件不存在，备份文件失败!");
			return 0;
		}
		
		String baseDirPath = baseDir.getAbsolutePath();
		//System.out.append(baseDirPath);
		File targetFile = new File(toFileName);
		
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFile));
			if (fileName.equals("*")) {
				FileOperationUntil.dirToZip(baseDirPath, baseDir, out);
			}else {
				File file = new File(baseDir,fileName);
				if (file.isFile()) {
					FileOperationUntil.fileToZip(baseDirPath, file, out);
				}else {
					FileOperationUntil.dirToZip(baseDirPath, file, out);
				}
			}
			out.close();
			WriteLog.writeLog("admin", "备份成功!");
			return flag;
		} catch (Exception e) {
			// TODO: handle exception
			WriteLog.writeLog("admin", "读写异常，备份失败!");
			throw new com.ucac.exception.IOException("文件读写异常");
		}
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo 
	 * @param zipFileName 需要恢复的文件
	 * @param targetBaseDirName 需要恢复文件的路径或者文件夹
	 * @return
	 * @throws com.ucac.exception.IOException 
	 * @returntype int 0表示失败，1表示成功
	 */
	private static int upZipFile(String zipFileName,String targetBaseDirName) throws com.ucac.exception.IOException{	//解压缩文件
		int flag = 1;
		
		if (!targetBaseDirName.endsWith(File.separator)) {
			targetBaseDirName +=File.separator;
		}
		try {
			
			ZipFile zipFile =  new ZipFile(zipFileName);
			ZipEntry entry = null;
			String entryName = null;
			String targetFileName = null;
			byte[] buffer = new byte[4096];
			int bytes_read;
			Enumeration entrys = zipFile.entries();
			
			while (entrys.hasMoreElements()) {
				entry = (ZipEntry) entrys.nextElement();
				entryName = entry.getName();
				targetFileName = targetBaseDirName + entryName;
				if (entry.isDirectory()) {
					new File(targetFileName).mkdirs();
					continue;
				}else{
					new File(targetFileName).getParentFile().mkdirs();
				}
				
				File targetFile = new File(targetFileName);
				FileOutputStream os =  new FileOutputStream(targetFile);
				InputStream is = zipFile.getInputStream(entry);
				while ((bytes_read = is.read(buffer)) != -1) {
					os.write(buffer, 0, bytes_read);				
				}
				os.close();
				is.close();	
			}
			WriteLog.writeLog("admin", "恢复文件成功!");
			return flag;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			WriteLog.writeLog("admin", "读写异常,恢复失败!");
			throw new com.ucac.exception.IOException("文件读写异常");
		}		
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo 
	 * @param baseDirPath
	 * @param dir
	 * @param out
	 * @throws FileNotExistException 
	 * @throws com.ucac.exception.IOException 
	 * @returntype void
	 */
	private static void dirToZip(String baseDirPath,File dir,ZipOutputStream out) throws com.ucac.exception.IOException, FileNotExistException{
		
		if (dir.isDirectory()&&dir.exists()) {
			File[] files = dir.listFiles();
			if (files.length == 0) {
				ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));
				try {
					out.putNextEntry(entry);
					out.closeEntry();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					FileOperationUntil.fileToZip(baseDirPath, files[i], out);
				}else{
					FileOperationUntil.dirToZip(baseDirPath, files[i], out);
				}
		   }
		}else{
			throw new FileNotExistException("Sorry，文件不存在!");
		}
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo 
	 * @param baseDirPath
	 * @param file
	 * @param out
	 * @throws com.ucac.exception.IOException 
	 * @throws FileNotExistException 
	 * @returntype void
	 */
	private static void fileToZip(String baseDirPath,File file,ZipOutputStream out) throws com.ucac.exception.IOException, FileNotExistException {
	
		FileInputStream in = null;
		ZipEntry entry = null;
		byte[] buffer = new byte[4096];
		int bytes_read;
		
		if (file.isFile()) {
			try {
				in = new FileInputStream(file);
				entry =  new ZipEntry(getEntryName(baseDirPath, file));
				out.putNextEntry(entry);
				while ((bytes_read = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytes_read);				
				}
				out.closeEntry();
				in.close();	
			} catch (IOException e) {
				// TODO: handle exception
				throw new com.ucac.exception.IOException("文件读写异常");
			}
		}
	}
	/**
	 * 
	 * laihuiqiang
	 * @todo 
	 * @param baseDirPath
	 * @param file
	 * @return
	 * @returntype String
	 */
	private static String getEntryName(String baseDirPath,File file) {
		
		if (!baseDirPath.endsWith(File.separator)) {
			baseDirPath +=File.separator;
		}
		String filePath = file.getAbsolutePath();		
		if (file.isDirectory()) {
			filePath +="/";
		}
		int index = filePath.indexOf(baseDirPath);
		
		return filePath.substring(index + baseDirPath.length());
	}
	/**
	 * 
	 * @param value
	 * @return 获取配置文件的路径
	 * @throws  
	 */
	public static String getPath(int value) {
	String path = null;
	SAXReader saxReader = new SAXReader();
	InputStream inputStream = FileOperationUntil.class.getClassLoader()
			.getResourceAsStream("FileOperation.xml");
	Document document = null;
	try {
		document = saxReader.read(inputStream);
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	List list = document.selectNodes("/root/file");
	Iterator itr = list.iterator();
	while (itr.hasNext()) {
		Element attribute = (Element)itr.next();
		if (value == 1) {
			if (attribute.attributeValue("function").equals("backupDatabase")) {
				path = attribute.elementText("path");
			}
		}else if (value == 2) {
			if (attribute.attributeValue("function").equals("materialName")) {
				path = attribute.elementText("path");
			}
		}else if (value == 3) {
			if (attribute.attributeValue("function").equals("backupAll")) {
				path = attribute.elementText("path");
			}
		}else if (value == 4) {
			if (attribute.attributeValue("function").equals("restoreDatabasePath")) {
				path = attribute.elementText("path");
			}
		}else if (value == 5) {
			if (attribute.attributeValue("function").equals("mysqlBinPath")) {
				path = attribute.elementText("path");
			}
		}else if (value == 6) {
			if (attribute.attributeValue("function").equals("databaseName")) {
				path = attribute.elementText("path");
			}
		}
		else{
			if (attribute.attributeValue("function").equals("restore")) {
				path = attribute.elementText("path");
			}
		}
	}
	try {
		URLDecoder.decode(path,"UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return path;
	}
}
