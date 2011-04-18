package net.retsat1.starlab.smssender.dao;

import net.retsat1.starlab.smssender.dto.SmsMessage;

/**
 * Alarm manager, you can set alarm or delete alarm.
 * 
 * @author mario
 * 
 */
public interface AlarmDao {

    public void setAlarm(SmsMessage smsMessage);

    public void deleteAlarm(SmsMessage smsMessage);

    public void deleteAlarm(Integer id);

}
