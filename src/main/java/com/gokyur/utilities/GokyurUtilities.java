package com.gokyur.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
	
	public static Date convertLocalDateTimeToServer(String dateString) throws ParseException {
		String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
		
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        Date date = formatter.parse(dateString);
        TimeZone tz = TimeZone.getDefault();

        // From TimeZone Local
        System.out.println("TimeZone : " + tz.getID() + " - " + tz.getDisplayName());
        System.out.println("TimeZone : " + tz);
        System.out.println("Date (Local) : " + formatter.format(date));

        // To TimeZone Server
        SimpleDateFormat sdfServer = new SimpleDateFormat(DATE_FORMAT);
        TimeZone tzInServer = TimeZone.getTimeZone("Atlantic/Bermuda");
        sdfServer.setTimeZone(tzInServer);

        String sDateInServer = sdfServer.format(date); // Convert to String first
        Date dateInServer = formatter.parse(sDateInServer); // Create a new Date object

//        System.out.println("\nTimeZone : " + tzInServer.getID() + " - " + tzInServer.getDisplayName());
//        System.out.println("TimeZone : " + tzInServer);
//        System.out.println("Date (Server) (String) : " + sDateInServer);
//        System.out.println("Date (Server) (Object) : " + formatter.format(dateInServer));
        
        return dateInServer;
	}
	
	public static long checkTime(Date lastdate) throws ParseException {
		
		Date nowDate = GokyurUtilities.getNow();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date lastd = lastdate;
		
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date date = inputFormat.parse(lastd.toString());

		// Format date into output format
		DateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String outputString = outputFormat.format(date);
		
		lastd = format.parse(outputString);
		
		long difference = nowDate.getTime() - lastd.getTime(); 
		
		//System.out.println("Time difference is "+difference);
		
		return difference;
	}
}
