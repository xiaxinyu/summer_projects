package com.file.joiner.entity.timestamp;

import java.util.Date;

/**
 * @author summer.xia
 */
public class TimestampTextRow {
	private String prefix;
	private Date timestamp;
	private String st;
	private String rowText;
	
	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

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
		return "TimestampTextRow [prefix=" + prefix + ", st=" + st + ", rowText=" + rowText + "]";
	}
}
