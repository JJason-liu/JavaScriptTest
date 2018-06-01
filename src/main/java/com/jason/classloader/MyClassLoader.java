package com.jason.classloader;

import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader {

	private static MyClassLoader instance = new MyClassLoader();

	public static MyClassLoader getInstance() {
		return instance;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		try {
			// 这个getClassInputStream根据情况实现
			String filename = name.replace('.', '/') + ".class";

			InputStream is = getClass().getResourceAsStream(filename);

			if (is == null) {
				return super.loadClass(name);
			}
			byte[] bt = new byte[is.available()];
			is.read(bt);
			return defineClass(name, bt, 0, bt.length);
		} catch (IOException e) {
			throw new ClassNotFoundException("Class " + name + " not found.");
		}
	}

	@Override
	protected Class<?> findClass(String name) {
		System.out.println("代用");
		Class<?> defineClass = null;
		return defineClass;
	}

}
