package com.file.joiner.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.file.joiner.entity.timestamp.TimestampTextFile;

public class TextFileHelper {
	public static TimestampTextFile readTextFile(File file) {
		return null;
	}
	
	public static List<TimestampTextFile> readTextFiles(List<File> files){
		List<TimestampTextFile> result = null;
		if(files != null && files.size() > 0){
			result = new ArrayList<TimestampTextFile>();
			for (File file : files) {
				result.add(readTextFile(file));
			}
		}
		return result;
	}
}