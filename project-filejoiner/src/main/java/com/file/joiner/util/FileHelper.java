package com.file.joiner.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author summer.xia
 */
public class FileHelper {
	private static Logger logger = Logger.getLogger(FileHelper.class);

	public static List<File> getFiles(String directory, String excludeFileName) {
		List<File> result = new ArrayList<File>();
		File file = new File(directory);
		if (file.isFile() && file.getName().indexOf(excludeFileName) < 0) {
			result.add(file);
			return result;
		} else {
			File[] array = file.listFiles();
			if (array == null || array.length <= 0) {
				return result;
			}
			for (int i = 0; i < array.length; i++) {
				File f = array[i];
				if (f.isFile() && f.getName().indexOf(excludeFileName) < 0) {
					result.add(f);
				} else {
					List<File> subArray = getFiles(f.getAbsolutePath(), excludeFileName);
					if (subArray != null && subArray.size() > 0) {
						result.addAll(subArray);
					}
				}
			}
		}
		return result;
	}

	public static void generateFie(StringBuffer sb, String path) {
		FileWriter fw = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			fw = new FileWriter(path);
			fw.write(sb.toString());
		} catch (IOException e) {
			logger.error("Generating file has error.", e);
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
}
