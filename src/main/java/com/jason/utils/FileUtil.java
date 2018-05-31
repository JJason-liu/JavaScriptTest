package com.jason.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.function.Predicate;

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

	public static void getFiles(String path, String type, List<File> filelist, Predicate<String> condition) {
		File file = new File(path);
		if (file != null) {
			String name = file.getName();
			if (file.isDirectory()) {
				File[] listFiles = file.listFiles(new FileFilter() {

					@Override
					public boolean accept(File file) {
						if (file.isDirectory()) {
							return true;
						}
						return file.getAbsolutePath().endsWith(type);
					}
				});
				for (File file2 : listFiles) {
					getFiles(file2.getAbsolutePath(), type, filelist, condition);
				}
			} else if (condition == null || condition.test(path)) {
				filelist.add(file);
				log.error("加载文件:" + name);
			}
		}
	}

	/**
	 * lambd 表达式一个比较操蛋的语法，感觉就是预先写出一个返回值为boolean类型的逻辑，
	 * 调用时只需要用.test方法就可以比较该对象是否满足预先写好的条件,就像是个过滤器
	 * 
	 * @param str
	 * @param condition
	 */
	public static void testPredicate(String str, Predicate<String> condition) {
		if (condition.test(str)) {
			log.error("卧槽，居然是这个语法！！！");
		}
	}

	public static void main(String[] args) {
		// FileUtil.delDirectory("C:\\Users\\Administrator.PC-20180420QNNV\\Desktop\\cluster-demo-master");
		FileUtil.testPredicate("不晓得写啥子", s -> {
			if (s.contains("写啥子")) {
				return true;
			}
			return false;
		});
	}
}
