package net.retsat1.starlab.smssender.dao;

import java.util.ArrayList;
import java.util.List;

import net.retsat1.starlab.smssender.conventer.CursorConventer;
import net.retsat1.starlab.smssender.conventer.PhoneContactConventer;
import net.retsat1.starlab.smssender.conventer.PhoneNumberConventer;
import net.retsat1.starlab.smssender.dto.PhoneContactDTO;
import net.retsat1.starlab.smssender.dto.PhoneNumberDTO;
import net.retsat1.starlab.smssender.utils.MyLog;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class PhoneContactDaoImpl implements PhoneContactDao {

    private static final String TAG = "PhoneContactDaoImpl";

    private static final String SELECTION_CONTACT_WITH_NUMBERS = ContactsContract.Contacts.HAS_PHONE_NUMBER + "='1'";
    private static final String[] PROJECTION_CONTACT_WITH_NUMBERS = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER, };

    private Context context;

    private CursorConventer<PhoneNumberDTO> phoneNumberConventer;
    private CursorConventer<PhoneContactDTO> phoneContactConventer;

    public PhoneContactDaoImpl(Context context) {
        this.context = context;
        phoneNumberConventer = new PhoneNumberConventer();
        phoneContactConventer = new PhoneContactConventer();
    }

    /**
     * Tu narazie jest ściernisko ... ale będzie sanfrancisko, a tam gdzie ten
     * 
     */
    @Override
    public List<PhoneContactDTO> getAllPhoneContact() {
        List<PhoneContactDTO> list = new ArrayList<PhoneContactDTO>();
        Cursor cursor = getCursorForAllContactsWithNumbers();
        while (cursor.moveToNext()) {
            PhoneContactDTO contact = phoneContactConventer.convert(cursor);
            Cursor pCur = getPhoneCursorForContactID(contact.id);
            MyLog.d(TAG, "Contact " + contact.id + " how many phone numbers " + pCur.getCount());
            while (pCur.moveToNext()) {
                PhoneNumberDTO phone = phoneNumberConventer.convert(pCur);
                contact.addPhoneNumber(phone);
            }
            pCur.close();
            list.add(contact);
        }
        cursor.close();
        return list;
    }

    private Cursor getPhoneCursorForContactID(String id) {
        String[] selection = new String[] { id };
        String[] phoneProjection = new String[] { ContactsContract.CommonDataKinds.Phone.DATA, ContactsContract.CommonDataKinds.Phone.TYPE };
        String where = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
        return context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phoneProjection, where, selection, null);
    }

    private Cursor getCursorForAllContactsWithNumbers() {
        return context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, PROJECTION_CONTACT_WITH_NUMBERS, SELECTION_CONTACT_WITH_NUMBERS, null,
                null);
    }

    @Override
    public PhoneContactDTO getDisplayNameByNumber(String number) {
        return null;
    }

    @Override
    public PhoneContactDTO getNumberByDisplayName(String number, int phoneType) {
        return null;
    }

}
