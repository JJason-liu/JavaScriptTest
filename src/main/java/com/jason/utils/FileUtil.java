package com.jason.utils;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author 有泪的北极星 qq: 76598166
 * @date 2018年5月31日 上午11:48:01
 */
public class FileUtil {

	private static final Logger log = LogManager.getLogger(FileUtil.class);

	public static boolean delDirectory(String path) {
		if (StringUtil.isEmpty(path)) {
			log.error("输入的文件名有误！！！");
			return false;
		}
		File file = new File(path);
		if (!file.isDirectory() || !file.exists()) {
			log.error("删除文件错误！！！");
			return false;
		}
		// 文件中还有子文件的情况
		File[] listFiles = file.listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			File childFile = listFiles[i];
			String absolutePath = childFile.getAbsolutePath();
			if (childFile.isDirectory()) {
				if (!delDirectory(absolutePath)) {
					break;
				}
			} else if (childFile.isFile()) {
				if (!delFile(childFile, absolutePath)) {
					break;
				}
			}
		}
		return file.delete();
	}

	public static boolean delFile(File file, String path) {
		if (file != null && file.exists() && file.isFile()) {
			file.delete();
			log.error("删除文件:" + file.getName());
			return true;
		}
		log.error("删除文件失败:" + path);
		return false;
	}

	public static void main(String[] args) {
		FileUtil.delDirectory("C:\\Users\\Administrator.PC-20180420QNNV\\Desktop\\cluster-demo-master");
	}
}
