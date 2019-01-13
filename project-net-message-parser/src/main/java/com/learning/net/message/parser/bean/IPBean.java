package com.learning.net.message.parser.bean;

import com.learning.net.message.parser.constant.IPTypeEnum;

public class IPBean {
	private Integer version;
	private Integer headerLength;
	private String diffServiceField;
	private Integer totalLength;
	private String identification;
	private String flags;
	private Integer timeToLive;
	private IPTypeEnum protocol;
	private String headerCheckSum;
	private String source;
	private String destination;
	private String tcpMessage;
	private TCPBean tcpBean;

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
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public Integer getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(Integer timeToLive) {
		this.timeToLive = timeToLive;
	}

	public IPTypeEnum getProtocol() {
		return protocol;
	}

	public void setProtocol(IPTypeEnum protocol) {
		this.protocol = protocol;
	}

	public String getHeaderCheckSum() {
		return headerCheckSum;
	}

	public void setHeaderCheckSum(String headerCheckSum) {
		this.headerCheckSum = headerCheckSum;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTcpMessage() {
		return tcpMessage;
	}

	public void setTcpMessage(String tcpMessage) {
		this.tcpMessage = tcpMessage;
	}

	public TCPBean getTcpBean() {
		return tcpBean;
	}

	public void setTcpBean(TCPBean tcpBean) {
		this.tcpBean = tcpBean;
	}

	@Override
	public String toString() {
		return "IPBean [version=" + version + ", headerLength=" + headerLength + ", diffServiceField="
				+ diffServiceField + ", totalLength=" + totalLength + ", identification=" + identification + ", flags="
				+ flags + ", timeToLive=" + timeToLive + ", protocol=" + protocol + ", headerCheckSum=" + headerCheckSum
				+ ", source=" + source + ", destination=" + destination + "]";
	}
}