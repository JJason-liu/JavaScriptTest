package com.jason.classloader;

import com.jason.utils.StringUtil;

public class MyClassLoader extends ClassLoader {

	private static MyClassLoader instance = new MyClassLoader();

	public static MyClassLoader getInstance() {
		return instance;
	}

	public Class load(String fileName) {
		if (!StringUtil.isEmpty(fileName)) {
			try {
				return super.loadClass(fileName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
