package net.retsat1.starlab.smssender.dto;

import net.retsat1.starlab.smssender.providers.SheduleSmsContentProvider;
import android.net.Uri;

public class SmsMessage {
	//intent 
	public static final String SENDER = "SENDER";
	public static final String RECEIVER = "RECEIVER";
	public static final String MESSAGE = "MESSAGE";
	public static final String DATA = "DATA";
	//database
	public static final String SMS_ID = "_id";
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.marek.sms.row";
	public static final Uri CONTENT_URI  = Uri.parse("content://"
			 + SheduleSmsContentProvider.PROVIDER_NAME + "/sms");
	public String sender;
	public String number;
	public String message;
	public long data;
	
	public SmsMessage() {
	}
	public SmsMessage(String number, String message) {
		this.number = number;
		this.message = message;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return number;
	}
	public void setReceiver(String receiver) {
		this.number = receiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SmsMessage [sender=" + sender + ", receiver=" + number
				+ ", message=" + message + ", data=" + data + "]";
	}
	
	
	
	
}
