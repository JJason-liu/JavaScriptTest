package com.jason.vertx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author 有泪的北极星 qq: 76598166
 * @date 2018年5月30日 上午11:09:09
 */
public class ResourceLoader {

	private static ResourceLoader instance = new ResourceLoader();

	private static Map<String, Properties> configMap = new HashMap<>();

	/** 默认的配置文件 */
	private static String DEFAULT_CONFIG_FILE = "vertx.properties";

	public static ResourceLoader getInstance() {
		return instance;
	}

	/**
	 * 获取配置文件
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Properties getProperties(String fileName) throws FileNotFoundException, IOException {
		Properties properties = configMap.get(fileName);
		if (properties != null) {
			return properties;
		}
		String path = this.getClass().getClassLoader().getResource(fileName).getPath();
		properties = new Properties();
		properties.load(new FileInputStream(new File(path)));
		configMap.put(fileName, properties);
		return properties;
	}

	private String getValue(String key) throws FileNotFoundException, IOException {
		return getProperties(DEFAULT_CONFIG_FILE).getProperty(key);
	}

	public Object getValue(String key, Object type) throws FileNotFoundException, IOException {
		if (type == Boolean.class) {
			return Boolean.parseBoolean(getValue(key));
		} else if (type == Integer.class) {
			return Integer.parseInt(getValue(key));
		} else if (type == Long.class) {
			return Long.parseLong(getValue(key));
		} else if (type == Double.class) {
			return Double.parseDouble(getValue(key));
		}
		return null;
	}
}
