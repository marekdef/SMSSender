package net.retsat1.starlab.smssender.conventer;

import net.retsat1.starlab.smssender.dto.PhoneContactDTO;
import net.retsat1.starlab.smssender.dto.PhoneNumberDTO;
import android.database.Cursor;
import android.provider.Contacts.Phones;

public class SimpleContactOldConventer implements CursorConventer<PhoneContactDTO> {

    @Override
    public PhoneContactDTO convert(Cursor cursor) {
        PhoneContactDTO contact = new PhoneContactDTO();
        contact.id = cursor.getString(cursor.getColumnIndex(Phones._ID));
        contact.displayName = cursor.getString(cursor.getColumnIndex(Phones.NAME));
        contact.phoneNumber = new PhoneNumberDTO();
        contact.phoneNumber.number = cursor.getString(cursor.getColumnIndex(Phones.NUMBER));
        contact.phoneNumber.phoneType = cursor.getInt(cursor.getColumnIndex(Phones.TYPE));
        return contact;
    }

}
