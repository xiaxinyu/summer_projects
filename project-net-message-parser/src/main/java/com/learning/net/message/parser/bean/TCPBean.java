package com.learning.net.message.parser.bean;

public class TCPBean {
	private Integer sourcePort;
	private Integer destinationPort;

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

	@Override
	public String toString() {
		return "TCPBean [sourcePort=" + sourcePort + ", destinationPort=" + destinationPort + "]";
	}
}
