package com.zwxq.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
/**
 * 文件操作工具类
 *
 */
public class FileUtil {

	private static final String SPLIT = ";";
	
	/**
	 * 删除文件夹或文件
	 *
	 * @param fileName
	 */
	public static void deleteFile(String fileName) {
		File file = new File(fileName);
		deleteFile(file);
	}

	/**
	 * 删除文件夹或文件
	 *
	 * @param file
	 */
	public static void deleteFile(File file) {

		if (!file.exists())
			return;

		if (file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
		}

		file.delete();
	}

	/**
	 * 判断本地文件或者目录是否存在
	 *
	 * @parma fullFileName 文件路径+文件名称 如:D:\\test\\test.jsp
	 * @return boolean
	 */
	public static boolean isExist(String fullFileName) {
		if ((null == fullFileName) || ("".equals(fullFileName))) {
			return false;
		}
		fullFileName = fullFileName.replace("//", "\\");
		File fl = new File(fullFileName);
		if (fl.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建本地目录
	 *
	 * @parma dirs 目录名称
	 * @return 是否成功
	 * @throws Exception
	 */
	public static boolean makeDir(String dirs) throws Exception {
		boolean result = false;
		File file = new File(dirs);
		// 创建目录
		result = file.mkdirs();
		return result;
	}

	/**
	 * 创建文本文件
	 *
	 * @parma fullFileName 文件路径+文件名称
	 * @parma txt 文件内容
	 * @return 是否成功
	 * @throws Exception
	 */
	public static void createFile(String fullFileName, String contents) throws Exception {

		if (!isExist(fullFileName)) {
			makeDir(fullFileName.substring(0, fullFileName.lastIndexOf("/")));
		}

		OutputStreamWriter outputStream = null;
		try {
			outputStream = new OutputStreamWriter(new FileOutputStream(fullFileName), "UTF-8");
			outputStream.write(null == contents ? "" : contents);
			outputStream.close();
		} finally {
			outputStream.close();
		}
	}

	/**
	 * 读取文件为字节.
	 *
	 * @param filePath
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readByteArray(String filePath) throws Exception {
		FileInputStream in = null;
		ByteArrayOutputStream bout = null;
		byte[] orgData = null;
		try {
			in = new FileInputStream(new File(filePath));
			bout = new ByteArrayOutputStream();
			byte[] tmpbuf = new byte[1024];
			int count = 0;
			while ((count = in.read(tmpbuf)) != -1) {
				bout.write(tmpbuf, 0, count);
				tmpbuf = new byte[1024];
			}
			orgData = bout.toByteArray();
		} finally {
			if (null != bout) {
				bout.flush();
				bout.close();
			}
			if (null != in) {
				in.close();
			}
		}
		return orgData;
	}

	/**
	 * 下载Excel
	 * 
	 * @param response
	 * @param fileName  输出的文件名称
	 * @throws IOException
	 */
	public static Boolean downloadExcelFile(HSSFWorkbook workbook, HttpServletResponse response, String fileName) {
		OutputStream output;
		try {
			output = response.getOutputStream();
			response.setHeader("Content-disposition","attachment; filename=" + DateUtil.getCurrentDate("yyyyMMddHHmmss") +new String(fileName.getBytes("UTF-8"), "ISO8859-1")+ ".xls");
			response.setContentType("application/msexcel");
			workbook.write(output);
			output.flush();
			output.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param fileName  输出的文件名称
	 * @throws IOException
	 */
	public static Boolean downloadExcelFile(HttpServletResponse response, String fileName) {
		OutputStream output;
		File file = new File(fileName);
		if (file.exists()) {
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						fileInputStream);
				byte[] b = new byte[bufferedInputStream.available()];
				bufferedInputStream.read(b);
				output = response.getOutputStream();
				response.setHeader("Content-disposition","attachment; filename=" + DateUtil.getCurrentDate("yyyyMMddHHmmss") +new String("模板文件".getBytes("UTF-8"), "ISO8859-1")+ ".xls");
				response.setContentType("application/msexcel");
				output.write(b);
				output.flush();
				output.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}else {
			return false;
		}
		
	}
	
	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param fileName  输出的文件名称
	 * @throws IOException
	 */
	public static Boolean downloadExcelFile(HttpServletResponse response, File file) {
		OutputStream output;
		if (file.exists()) {
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						fileInputStream);
				byte[] b = new byte[bufferedInputStream.available()];
				bufferedInputStream.read(b);
				output = response.getOutputStream();
				response.setHeader("Content-disposition","attachment; filename=" + DateUtil.getCurrentDate("yyyyMMddHHmmss") +new String("模板文件".getBytes("UTF-8"), "ISO8859-1")+ ".xls");
				response.setContentType("application/msexcel");
				output.write(b);
				output.flush();
				output.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}else {
			return false;
		}
		
	}

	/**
	 * 上传文件
	 */
	public static String uploadFile(MultipartFile file,String path) {
		if (file.isEmpty()) { // 提示
			return "上传错误";
		}
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名称
		String fileNameExtention = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		// 生成真实的文件名称
		String realName = UUID.randomUUID().toString() + fileNameExtention;
		File dest = new File(path + "/" + realName);
		if (FileUtil.isExist(path)) {
			try {
				FileUtil.makeDir(path);
				file.transferTo(dest); // 保存文件
			} catch (Exception e) {
				e.printStackTrace();
				return "上传失败";
			}
		}
		return realName;
	}

	
}
