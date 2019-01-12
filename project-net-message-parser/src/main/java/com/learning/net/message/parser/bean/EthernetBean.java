package com.learning.net.message.parser.bean;

import com.learning.net.message.parser.constant.EthernetTypeEnum;

public class EthernetBean {
	private String destination;
	private String source;
	private  EthernetTypeEnum type;
	private String IPMessage;
	private IPBean ipBean;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public EthernetTypeEnum getType() {
		return type;
	}

	public void setType(EthernetTypeEnum type) {
		this.type = type;
	}

	public IPBean getIpBean() {
		return ipBean;
	}

	public void setIpBean(IPBean ipBean) {
		this.ipBean = ipBean;
	}

	public String getIPMessage() {
		return IPMessage;
	}

	public void setIPMessage(String iPMessage) {
		IPMessage = iPMessage;
	}

	@Override
	public String toString() {
		return "EthernetBean [destination=" + destination + ", source=" + source + ", type=" + type + "]";
	}
}