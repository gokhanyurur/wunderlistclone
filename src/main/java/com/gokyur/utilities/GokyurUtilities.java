package com.gokyur.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GokyurUtilities {

	public static String MD5(String md5) {
	try {
		java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		byte[] array = md.digest(md5.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		}
		return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
	return null;
	}
	
	public static Date getNow() throws ParseException {
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());	
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(timeStamp);
	}
}
