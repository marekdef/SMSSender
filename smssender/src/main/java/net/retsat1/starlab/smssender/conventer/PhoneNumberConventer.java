package net.retsat1.starlab.smssender.conventer;

import net.retsat1.starlab.smssender.dto.PhoneNumberDTO;
import android.database.Cursor;
import android.provider.ContactsContract;

public class PhoneNumberConventer implements CursorConventer<PhoneNumberDTO> {

    @Override
    public PhoneNumberDTO convert(Cursor cursor) {
        PhoneNumberDTO phone = new PhoneNumberDTO();
        phone.number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
        phone.phoneType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
        return phone;
    }

}
