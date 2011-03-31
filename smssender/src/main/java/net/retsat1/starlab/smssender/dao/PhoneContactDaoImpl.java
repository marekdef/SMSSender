package net.retsat1.starlab.smssender.dao;

import java.util.List;

import net.retsat1.starlab.smssender.dto.PhoneContactDTO;
import android.content.Context;

public class PhoneContactDaoImpl implements PhoneContactDao {

    private Context context;

    public PhoneContactDaoImpl(Context context) {
        this.context = context;
    }

    /**
     * Tu narazie jest ściernisko ... ale będzie sanfrancisko, a tam gdzie ten
     * null będą zwracane kontakty.
     */
    @Override
    public List<PhoneContactDTO> getAllPhoneContact() {
        return null;
    }

    @Override
    public String getDisplayNameByNumber(String number) {
        return null;
    }

    @Override
    public String getNumberByDisplayName(String number, int phoneType) {

        return null;
    }

}
