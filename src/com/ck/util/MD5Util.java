package com.ck.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.common.util.StringHelper;
public class MD5Util {
	
	/**
	 * 对指定明文进行加密
	 * @param inputStr 明文数据
	 * @return 十六进制标示的字符串
	 */
	public static String encrypt(String inputStr){
		if(StringHelper.isEmpty(inputStr)){
			inputStr = "123456";
		}
		MessageDigest  md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		try {
			md.update(inputStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return StringHelper.toString(md.digest());
	}
}
