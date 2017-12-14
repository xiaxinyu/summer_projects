package com.file.joiner.core;

import java.util.List;

import org.apache.log4j.Logger;

import com.file.joiner.entity.timestamp.TimestampTextFile;
import com.file.joiner.entity.timestamp.TimestampTextRow;
import com.file.joiner.util.FileHelper;
import com.file.joiner.util.TextFileHelper;

public class TimestampFileJoiner {
	private static Logger logger = Logger.getLogger(TimestampFileJoiner.class);

	public static void join(String directory, String joinerName, String timestampFmt) {
		List<TimestampTextFile> files = TextFileHelper.readTextFiles(FileHelper.getFiles(directory, joinerName),
				timestampFmt);
		if (files != null && files.size() > 0) {
			TimestampTextFile result = new TimestampTextFile();
			result.setFileName(joinerName);
			List<TimestampTextRow> joinRows = null;
			for (int i = 0; i < files.size(); i++) {
				List<TimestampTextRow> rows = files.get(i).getTextRows();
				if (rows != null && rows.size() > 0) {
					if (joinRows == null) {
						joinRows = rows;
						continue;
					}
					joinRows.addAll(rows);
				}
			}
			TimestampTextRow[] finalRows = sort(joinRows);
			TextFileHelper.outPut(finalRows, directory, joinerName);
		} else {
			logger.info(String.format("Not files are found in directory,%", directory));
		}
	}

	private static TimestampTextRow[] sort(List<TimestampTextRow> rows) {
		TimestampTextRow[] result = null;
		if (rows != null && rows.size() > 0) {
			result = new TimestampTextRow[rows.size()];
			rows.toArray(result);
			for (int i = 1; i < result.length; i++) {
				for (int j = 0; j < (result.length-i); j++) {
					if (result[j].getTimestamp().after(result[j + 1].getTimestamp())) {
						TimestampTextRow t = result[j];
						result[j] = result[j + 1];
						result[j + 1] = t;
					}
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Integer[] tms = new Integer[] { 67, 5, 6, 8, 212321, 2, 6 };
		Integer t = 0;
		for (int i = 1; i < tms.length; i++) {
			for (int j = 0; j < (tms.length - i); j++) {
				Integer c = tms[j];
				Integer n = tms[j + 1];
				if (c > n) {
					t = c;
					tms[j] = n;
					tms[j + 1] = t;
				}
			}
		}

		for (int i : tms) {
			System.out.print(i + "\t");
		}
	}
}
