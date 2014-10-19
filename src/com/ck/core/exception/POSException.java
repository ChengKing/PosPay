package com.ck.core.exception;

public class POSException extends Exception {

	private int code;
	
	private String msg;
	
	private Throwable e;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Throwable getE() {
		return e;
	}

	public void setE(Throwable e) {
		this.e = e;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public POSException(int code,String msg,Throwable e){
		super();
		this.code = code;
		this.msg = msg;
		this.e = e;
	}

}
