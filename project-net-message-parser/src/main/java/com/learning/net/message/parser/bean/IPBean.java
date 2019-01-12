package com.learning.net.message.parser.bean;

public class IPBean {
	private Integer version;
	private Integer headerLength;
	private String diffServiceField;
	private Integer totalLength;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(Integer headerLength) {
		this.headerLength = headerLength;
	}
	
	public String getDiffServiceField() {
		return diffServiceField;
	}

	public void setDiffServiceField(String diffServiceField) {
		this.diffServiceField = diffServiceField;
	}
	
	public Integer getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}

	@Override
	public String toString() {
		return "IPBean [version=" + version + ", headerLength=" + headerLength + ", diffServiceField="
				+ diffServiceField + ", totalLength=" + totalLength + "]";
	}
}