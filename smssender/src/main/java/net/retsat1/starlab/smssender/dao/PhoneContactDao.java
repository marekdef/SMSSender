package net.retsat1.starlab.smssender.dao;

import java.util.List;

import net.retsat1.starlab.smssender.dto.PhoneContactDTO;

public interface PhoneContactDao {
    public List<PhoneContactDTO> getAllPhoneContact();

    public PhoneContactDTO getDisplayNameByNumber(String number);

    public PhoneContactDTO getNumberByDisplayName(String name, int phoneType);

}
