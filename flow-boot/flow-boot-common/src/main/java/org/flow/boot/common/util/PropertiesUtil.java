package org.flow.boot.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * load all properties file from config directory that under in root class path
 * 
 * @author songyz
 *
 */
public class PropertiesUtil {

	private volatile static boolean hasInit = false;

	private static Set<String> loadedFiles = new HashSet<String>();

	private static Properties appProps = new Properties();

	private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

	protected static void init() {
		if (hasInit)
			return;

		synchronized (log) {
			if (hasInit)
				return;
			URL u = PropertiesUtil.class.getClassLoader().getResource("");
			String rootPath = new File(u.getFile()).getAbsolutePath();
			if (File.separatorChar == '/')
				rootPath = "/" + rootPath;
			File confPath = new File(rootPath + "/config");
			File[] files = confPath.listFiles();
			if (files == null) {
				log.warn("not found properties file");
				return;
			}

			for (File file : files) {
				if (file.getPath().endsWith(".properties"))
					toLoad(file);
			}
			hasInit = true;
		}
	}

	public synchronized static void toLoad(File file) {
		if (loadedFiles.contains(file.getPath()))
			return;

		InputStream in = null;
		try {
			in = new FileInputStream(file);
			appProps.load(in);
			loadedFiles.add(file.getPath());
		} catch (Exception exp) {
			log.warn("meet error when load properties from " + file.getPath(), exp);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception exp) {
				}
			}
		}
	}

	public static void setProp(Properties props) {
		appProps.putAll(props);
	}

	/**
	 * if not found required key, return defaultValue.
	 * 
	 * Ignoring case, if property value is 'true', 'yes', 'y', '0', ture will be
	 * returned, otherwise will return false.
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getValue(String key, boolean defaultValue) {
		init();

		String value = appProps.getProperty(key);
		if (value == null)
			return defaultValue;
		else {
			value = value.toUpperCase();
			if (value.equals("TRUE") || value.equals("YES") || value.equals("Y") || value.equals("0"))
				return true;
			else
				return false;
		}
	}

	/**
	 * return property value, if not found return defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(String key, String defaultValue) {
		init();

		String value = appProps.getProperty(key);
		if (value == null)
			return defaultValue;
		else
			return value;
	}

	/**
	 * get property value and convert to int, if not found nor convert failed
	 * return defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getValue(String key, int defaultValue) {
		init();

		String value = appProps.getProperty(key);
		if (value == null)
			return defaultValue;
		else {
			try {
				return Integer.parseInt(value);
			} catch (Exception exp) {
				log.warn(key + " value isn't number: " + value);
				return defaultValue;
			}
		}
	}
}
