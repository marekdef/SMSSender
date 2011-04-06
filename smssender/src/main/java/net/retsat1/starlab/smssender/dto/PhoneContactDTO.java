package net.retsat1.starlab.smssender.dto;

public class PhoneContactDTO {
    public String displayName;
    public PhoneNumberDTO phoneNumber;
    public String id;

    public void addPhoneNumber(PhoneNumberDTO phone) {
        if (phoneNumber == null) {
            phoneNumber = phone;
        } else {
            PhoneNumberDTO tempPhoneNumber = phoneNumber.next;
            while (tempPhoneNumber != null) {
                tempPhoneNumber = tempPhoneNumber.next;
            }
            tempPhoneNumber.next = phone;
        }

    }
}
