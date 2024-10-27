package com.automation.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtility {

	public static String readDataFromPropertyFile(String path, String key) {

		File file = new File(path);
		FileInputStream fi = null;
		Properties propFile = new Properties();
		String data = null;
		try {
			fi = new FileInputStream(file);
			propFile.load(fi);
			data = propFile.getProperty(key);
			fi.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	public static int getSize(String Path) {
		File file = new File(Path);
		FileInputStream fi = null;
		Properties propFile = new Properties();
		int size = 0;
		try {
			fi = new FileInputStream(file);
			propFile.load(fi);
			size = propFile.size();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return size;

	}

}
