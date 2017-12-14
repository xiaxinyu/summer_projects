package com.file.joiner.entity.timestamp;

import java.util.List;

/**
 * @author summer.xia
 */
public class TimestampTextFile {
	private String fileName;
	private List<TimestampTextRow> textRows;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<TimestampTextRow> getTextRows() {
		return textRows;
	}

	public void setTextRows(List<TimestampTextRow> textRows) {
		this.textRows = textRows;
	}
}
