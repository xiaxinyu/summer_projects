package com.learning.net.message.parser.bean;

public class TCPBean {
	private Integer sourcePort;
	private Integer destinationPort;
	private Long sequenceNumber;
	private Long ackNumber;
	private Integer segmentLength;
	private Integer windowSize;
	private String checkSum;
	private Integer urgentPointer;
	private String  segmentData;

	public Integer getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(Integer sourcePort) {
		this.sourcePort = sourcePort;
	}

	public Integer getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(Integer destinationPort) {
		this.destinationPort = destinationPort;
	}
	
	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Long getAckNumber() {
		return ackNumber;
	}

	public void setAckNumber(Long ackNumber) {
		this.ackNumber = ackNumber;
	}

	public Integer getSegmentLength() {
		return segmentLength;
	}

	public void setSegmentLength(Integer segmentLength) {
		this.segmentLength = segmentLength;
	}
	
	public Integer getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(Integer windowSize) {
		this.windowSize = windowSize;
	}
	
	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public Integer getUrgentPointer() {
		return urgentPointer;
	}

	public void setUrgentPointer(Integer urgentPointer) {
		this.urgentPointer = urgentPointer;
	}

	public String getSegmentData() {
		return segmentData;
	}

	public void setSegmentData(String segmentData) {
		this.segmentData = segmentData;
	}

	@Override
	public String toString() {
		return "TCPBean [sourcePort=" + sourcePort + ", destinationPort=" + destinationPort + ", sequenceNumber="
				+ sequenceNumber + ", ackNumber=" + ackNumber + ", segmentLength=" + segmentLength + ", windowSize="
				+ windowSize + ", checkSum=" + checkSum + ", urgentPointer=" + urgentPointer + ", segmentData="
				+ segmentData + "]";
	}
}
