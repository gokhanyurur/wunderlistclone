package com.gokyur.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class TLSEmail {

	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (use authentication)
	   Use Authentication: Yes
	   Port for TLS/STARTTLS: 587
	 */
	public static void sendEmail(String toEmail, String userHash) {
		final String fromEmail = "wunderlistclone@gmail.com"; //requires valid gmail id
		final String password = "Gkhn.159753"; // correct password for gmail id
		//final String toEmail = "yururg@gmail.com"; // can be any email id 
		
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		
		String activationLink = "http://localhost:8080/wunderlistclone/activation/"+userHash;
		
		EmailUtil.sendEmail(session, toEmail,"Activate Your Account", "Welcome to Wunderlistclone! This application is made only for educational purpose. Here is your activation link: "+activationLink);
		
	}

	
}