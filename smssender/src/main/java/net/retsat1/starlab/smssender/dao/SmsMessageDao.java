package net.retsat1.starlab.smssender.dao;

import java.util.List;

import net.retsat1.starlab.smssender.dto.SmsMessage;

public interface SmsMessageDao {

    public boolean delete(SmsMessage smsMessage);

    public boolean update(SmsMessage smsMessage);

    public boolean insert(SmsMessage smsMessage);

    public SmsMessage searchByID(int smsId);

    public void delete(int id);

    public List<SmsMessage> getAllMessages();

}
