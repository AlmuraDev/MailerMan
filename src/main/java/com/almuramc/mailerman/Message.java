package com.almuramc.mailerman;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable{
	private String username, receiver;
	private String subject, date;
	private String message;
	
	public Message(String username, String receiver, String subject, String message) {
		this.username = username;
		this.receiver = receiver;
		this.subject = subject;
		this.message = message;
		date = currentTimeStamp();
	}

	public String getUsername() {
		return username;
	}
	public String getReceiver() {
		return receiver;
	}

	public String getSubject() {
		return subject;
	}

	public String getDate() {
		return date;
	}
	
	public String getMessage() {
		return message;
	}

	public static String currentTimeStamp() {
		Date d = new Date();
		SimpleDateFormat timeStampFormatter = new SimpleDateFormat("hh:mm:ss");
		return timeStampFormatter.format(d);
	}
	
	
}
