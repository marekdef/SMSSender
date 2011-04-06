package net.retsat1.starlab.smssender.dao;

import java.util.ArrayList;
import java.util.List;

import net.retsat1.starlab.smssender.conventer.CursorConventer;
import net.retsat1.starlab.smssender.conventer.SimpleContactOldConventer;
import net.retsat1.starlab.smssender.dto.PhoneContactDTO;
import android.content.Context;
import android.database.Cursor;
import android.provider.Contacts.Phones;

public class PhoneContactDaoOldImpl implements PhoneContactDao {

    private Context mContext;
    private CursorConventer<PhoneContactDTO> conventer;

    public PhoneContactDaoOldImpl(Context context) {
        this.mContext = context;
        conventer = new SimpleContactOldConventer();
    }

    @Override
    public List<PhoneContactDTO> getAllPhoneContact() {
        List<PhoneContactDTO> list = new ArrayList<PhoneContactDTO>();
        // Get a cursor with all phones
        Cursor cursor = mContext.getContentResolver().query(Phones.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            PhoneContactDTO contact = conventer.convert(cursor);
            list.add(contact);
        }
        return list;
    }

    @Override
    public PhoneContactDTO getDisplayNameByNumber(String number) {
        Cursor cursor = mContext.getContentResolver().query(Phones.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            PhoneContactDTO contact = conventer.convert(cursor);
            return contact;
        }
        return null;
    }

    @Override
    public PhoneContactDTO getNumberByDisplayName(String name, int phoneType) {
        Cursor cursor = mContext.getContentResolver().query(Phones.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            PhoneContactDTO contact = conventer.convert(cursor);
            return contact;
        }
        return null;
    }

}
