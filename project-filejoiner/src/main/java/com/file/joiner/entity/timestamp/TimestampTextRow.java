package com.file.joiner.entity.timestamp;

import java.util.Date;

/**
 * @author summer.xia
 */
public class TimestampTextRow {
	private Date timestamp;
	private String rowText;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getRowText() {
		return rowText;
	}

	public void setRowText(String rowText) {
		this.rowText = rowText;
	}

	@Override
	public String toString() {
		return "TimestampTextRow [timestamp=" + timestamp + ", rowText=" + rowText + "]";
	}
}
