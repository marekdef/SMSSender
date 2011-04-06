package net.retsat1.starlab.smssender.validators;

public class LenghtNumberValidator implements NumberValidator {

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

}
