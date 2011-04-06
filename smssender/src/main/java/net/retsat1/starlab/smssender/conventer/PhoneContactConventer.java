package net.retsat1.starlab.smssender.conventer;

import net.retsat1.starlab.smssender.dto.PhoneContactDTO;
import android.database.Cursor;
import android.provider.ContactsContract;

public class PhoneContactConventer implements CursorConventer<PhoneContactDTO> {

    @Override
    public PhoneContactDTO convert(Cursor cursor) {
        PhoneContactDTO contact = new PhoneContactDTO();
        contact.id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        contact.displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        return contact;
    }

}
