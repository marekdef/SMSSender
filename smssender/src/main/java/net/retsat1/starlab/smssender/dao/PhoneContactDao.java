package net.retsat1.starlab.smssender.dao;

import java.util.List;

import net.retsat1.starlab.smssender.dto.PhoneContactDTO;

public interface PhoneContactDao {
    public List<PhoneContactDTO> getAllPhoneContact();

    public String getDisplayNameByNumber(String number);

    public String getNumberByDisplayName(String number, int phoneType);

}
