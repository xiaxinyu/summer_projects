package com.file.joiner.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.file.joiner.entity.timestamp.TimestampTextFile;
import com.file.joiner.entity.timestamp.TimestampTextRow;

/**
 * @author summer.xia
 */
public class TextFileHelper {
	private static Logger logger = Logger.getLogger(TextFileHelper.class);

	public static void outPut(TimestampTextRow[] rows, String path, String joinerName) {
		if (rows != null && rows.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (TimestampTextRow row : rows) {
				sb.append(row.getPrefix() + " : " + row.getRowText() + "\n");
			}
			FileHelper.generateFie(sb, path + File.separator + joinerName + ".txt");
		}
	}

	public static List<TimestampTextFile> readTextFiles(List<File> files, String timestampFmt) {
		List<TimestampTextFile> result = null;
		if (files != null && files.size() > 0) {
			result = new ArrayList<TimestampTextFile>();
			for (int i = 0; i < files.size(); i++) {
				result.add(readTextFile(i, files.get(i), timestampFmt));
			}
		}
		return result;
	}

	private static TimestampTextFile readTextFile(int index, File file, String timestampFmt) {
		TimestampTextFile result = new TimestampTextFile();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String rowData = StringUtils.EMPTY;
			List<TimestampTextRow> rowTexts = new ArrayList<TimestampTextRow>();
			while ((rowData = br.readLine()) != null) {
				rowData = StringUtils.trim(rowData);
				if (StringUtils.isNotBlank(rowData)) {
					TimestampTextRow row = getTextRow(index, rowData, timestampFmt);
					if (row != null) {
						rowTexts.add(row);
					}
				}
			}
			result.setFileName(file.getName());
			result.setTextRows(rowTexts);
			br.close();
		} catch (IOException e) {
			logger.error(String.format("Getting text file has error."), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return result;
	}

	private static TimestampTextRow getTextRow(int index, String rowData, String timestampFmt) {
		TimestampTextRow row = null;
		String ds = rowData.substring(0, timestampFmt.length());
		if (StringUtils.isNotBlank(ds) && StringUtils.isNotBlank(ds.trim())) {
			Date d = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(timestampFmt);
				d = sdf.parse(ds);
			} catch (Exception e) {
				d = null;
				logger.error(String.format("Getting row text has error.RowText={%s}", rowData), e);
			}
			if (d != null) {
				row = new TimestampTextRow();
				row.setPrefix(String.format("%02d", index));
				row.setSt(ds);
				row.setTimestamp(d);
				row.setRowText(rowData);
			}
		}
		return row;
	}
}