package com.account.web.rest.face.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Summer.Xia on 9/1/2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CommonResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private String returnCode;
	private String returnMessage;

	public CommonResult(String returnCode, String returnMessage) {
		this.returnCode = returnCode;
		this.returnMessage = returnMessage;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
}
