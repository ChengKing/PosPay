package com.ck.core;

import org.jpos.iso.ISOMsg;

import com.ck.core.exception.POSException;

public interface IPosRequest {
	
	public String getCmd() throws POSException;
	
	public ISOMsg getISOMsg() throws POSException;

}
