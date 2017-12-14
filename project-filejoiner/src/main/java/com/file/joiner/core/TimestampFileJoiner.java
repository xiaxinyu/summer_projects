package com.file.joiner.core;

import java.util.List;

import com.file.joiner.entity.timestamp.TimestampTextFile;
import com.file.joiner.entity.timestamp.TimestampTextRow;
import com.file.joiner.util.FileHelper;
import com.file.joiner.util.TextFileHelper;

public class TimestampFileJoiner {

	public static void join(String directory, String joinerName) {
		List<TimestampTextFile> files = TextFileHelper.readTextFiles(FileHelper.getFiles(directory));
		if (files != null && files.size() > 0) {
			TimestampTextFile result = new TimestampTextFile();
			result.setFileName(joinerName);
			for (int i = 0; i < files.size(); i++) {
				int next = i+1;
				if(next < files.size()){
					List<TimestampTextRow> joinRows1 = files.get(i).getTextRows();
					List<TimestampTextRow> joinRows2 = files.get(i+1).getTextRows();
				}
			}
		} else {
			// TODO input log
		}
	}
	
	
	public 
	
}
