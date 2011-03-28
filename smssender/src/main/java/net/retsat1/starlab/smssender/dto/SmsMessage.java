package net.retsat1.starlab.smssender.dto;

import net.retsat1.starlab.smssender.providers.SheduleSmsContentProvider;
import net.retsat1.starlab.smssender.receiver.SmsStatusDeliveredReceiver;
import net.retsat1.starlab.smssender.receiver.SmsStatusSendReceiver;
import android.net.Uri;

public class SmsMessage {
	//intent and db columns names 
	public static final String SENDER = "SENDER"; //TODO I don't know if we need keep sender filed, because it will be always this same phone
	public static final String RECEIVER = "RECEIVER";
	public static final String MESSAGE = "MESSAGE";
	public static final String DATA = "DATA";	
	/**
	 * List of state you can find in 
	 * {@link SmsStatusSendReceiver#getResultCode()}
	 * <ul>
	 * <li>SmsMessage.UNSENT = -2</li>
	 * <li>Activity.RESULT_OK = -1</li>
	 * <li>SmsManager.RESULT_ERROR_GENERIC_FAILURE = 1</li>
	 * <li>SmsManager.RESULT_ERROR_NO_SERVICE = 4</li>
	 * <li>SmsManager.RESULT_ERROR_NULL_PDU = 3</li>
	 * <li>SmsManager.RESULT_ERROR_RADIO_OFF = 2</li>
	 * </ul>
	 */
	public static final String MESSAGE_STATUS = "MESSAGE_STATUS"; // -2 not sent yet
	public static final int STATUS_UNSENT = -2;
	public static final int STATUS_SENDING = -3;
	/**
	 * List of state you can find in 
	 * {@link SmsStatusDeliveredReceiver}
	 * <ul>
	 * <li>Activity.RESULT_OK = -1</li>
	 * <li>SmsManager.RESULT_ERROR_GENERIC_FAILURE = 1</li>
	 * </ul>
	 */
	public static final String DELIVERY_STATUS = "DELIVERY_STATUS"; // send, received, supplied
	
	/**
	 * Column name for database. Store time when user set sms.
	 */
	public static final String SETUP_DATE = "SETUP_DATE";
	/**
	 * Column name for database. Store time last sms status.
	 */
	public static final String STATUS_DATE = "STATUS_DATE";
	
	
	//database
	public static final String SMS_ID = "_id";
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.marek.sms.row";
	public static final Uri CONTENT_URI  = Uri.parse("content://"
			 + SheduleSmsContentProvider.PROVIDER_NAME + "/sms");
	public static final String DELIVERY_DATE = "DELIVERY_DATE";
	public String sender;
	public String number;
	public String message;
	public long dateOfSetup;
	public long dateOfStatus;
	public long deliveryDate;
	public int id;
	public int messageStatus;
	public int deliveryStatus;
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
				+ ", message=" + message + ", data=" + dateOfSetup + "]";
	}	
}
