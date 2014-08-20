package com.ucac.syw.util;

import static com.ucac.syw.util.Scalr.OP_ANTIALIAS;
import static com.ucac.syw.util.Scalr.OP_BRIGHTER;
import static com.ucac.syw.util.Scalr.resize;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.sf.jmimemagic.Magic;

import com.ucac.syw.util.Scalr.Method;

public class ImgCompresser {

	/**
	 * songyouwei
	 * @todo 遍历目录，压缩其中的图片类文件
	 * @param dir
	 */
	public static void compressDir(String dir) {
		for (File file : new File(dir).listFiles()) {
			if (file.isFile()) {
				boolean isImage = false;
				try {
					isImage = Magic.getMagicMatch(file, false, false).getMimeType().toLowerCase().startsWith("image");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (isImage) {
					compressAndCoverAImgFile(file);
				}
			}
		}
	}
	
	/**
	 * songyouwei
	 * @todo 压缩一个图片文件，覆盖原文件
	 */
	private static void compressAndCoverAImgFile(File imgFile) {
		InputStream is = null;
		try {
			is = new FileInputStream(imgFile);
			BufferedImage originImage = ImageIO.read(is);			
			BufferedImage resultImage = createThumbnail(originImage);
			String resultFilePath = imgFile.getAbsolutePath().replace('\\', '/').substring(0, imgFile.getAbsolutePath().lastIndexOf('.'));
			ImageIO.write(resultImage, "jpg", new File(resultFilePath+".jpg"));
			originImage.flush();
			resultImage.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * songyouwei
	 * @todo 图片压缩
	 * @param img
	 * @return
	 */
	private static BufferedImage createThumbnail(BufferedImage img) {
		// Create quickly, then smooth and brighten it.
		if (img.getWidth() > 500) {
			 img = resize(img, Method.SPEED, 500, OP_ANTIALIAS, OP_BRIGHTER);
			 img.flush();
		}
		return img;
	}
}
