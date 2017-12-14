package com.file.joiner;

import com.file.joiner.core.TimestampFileJoiner;
import com.file.joiner.util.Constants;

public class Application {
	private static final String timestampFmt = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String joinerName = Constants.NEW_FILE_NAME;
	
	public static void main(String[] args) {
		String directory = "d:\\test";
		TimestampFileJoiner.join(directory, joinerName, timestampFmt);
	}
}
