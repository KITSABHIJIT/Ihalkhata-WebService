package com.exp.webMethods;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReturnJson {
	private boolean success;
	private String msg;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
