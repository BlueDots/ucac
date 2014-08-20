package com.ucac.syw.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import com.ucac.syw.exception.UploadException;

public class Doc2HtmlUtil {

	/**
	 * songyouwei
	 * @todo 把Doc文档转化为Html文档并存储
	 * @param path 文件路径  docFile和HtmlFile都在这个目录下
	 * @param docFileName
	 * @param HtmlFileName
	 * @throws Throwable
	 */
	public static void convertAndSave(final String path,final String docFileName,final String HtmlFileName) throws UploadException {

		
		try {
			InputStream input = new FileInputStream(path + docFileName);
			HWPFDocument wordDocument = new HWPFDocument(input);
			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
			wordToHtmlConverter.setPicturesManager(new PicturesManager() {
				@Override
				public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
					String srcReference = docFileName.substring(0, docFileName.lastIndexOf('.')) + "/" + suggestedName;
					return srcReference;
				}
			});
			
			wordToHtmlConverter.processDocument(wordDocument);
			List pics = wordDocument.getPicturesTable().getAllPictures();
			if (pics != null) {
				File file = new File(path + docFileName.substring(0, docFileName.lastIndexOf('.')));
				if (!file.exists()) {
					file.mkdir();
				}
				for (int i = 0; i < pics.size(); i++) {
					Picture pic = (Picture) pics.get(i);
					try {
						pic.writeImageContent(new FileOutputStream(path + docFileName.substring(0, docFileName.lastIndexOf('.')) + "/" + pic.suggestFullFileName()));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			Document htmlDocument = wordToHtmlConverter.getDocument();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			DOMSource domSource = new DOMSource(htmlDocument);
			StreamResult streamResult = new StreamResult(outStream);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(domSource, streamResult);
			outStream.close();

			String content = new String(outStream.toByteArray());

			FileUtils.write(new File(path, HtmlFileName), content, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new UploadException(UploadException.errorMsg_io);
		}
	}
	
	/**
	* @throws IOException 
	 * @Author: songyouwei
	* @Title: changeCharSet 
	* @Description 解决乱码问题【把gb2312编码改为utf-8】
	 */
	public static void changeCharSet(String filePath) throws UploadException {
		
		try {
			File file = new File(filePath);
			org.jsoup.nodes.Document doc = Jsoup.parse(file, "utf-8");
			Elements content = doc.getElementsByAttributeValueStarting("content", "text/html;");
			for (Element meta : content) {
				meta.attr("content", "text/html; charset=UTF-8");
				System.out.println("修改content--------" + file.getName() + "---");
			}
			FileUtils.writeStringToFile(file, doc.html(),System.getProperty("file.encoding"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new UploadException(UploadException.errorMsg_io);
		}
	}
}
