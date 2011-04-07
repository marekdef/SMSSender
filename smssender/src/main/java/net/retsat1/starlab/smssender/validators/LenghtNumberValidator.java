package net.retsat1.starlab.smssender.validators;

import net.retsat1.starlab.smssender.R;

public class LenghtNumberValidator implements NumberValidator {

    /**
     * This method should answer for question: Can I send on this number ?
     */
    @Override
    public boolean isValid(String number) {
        if (number == null) {
            return false;
        }
        if (number.length() < 1) {
            return false;
        }
        return true;
    }

    @Override
    public int getErrorMessageRef() {
        return R.string.provide_number;
    }

}
