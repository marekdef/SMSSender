package net.retsat1.starlab.smssender.validators;

public class NumberHighPaidValidator implements NumberValidator {

    /**
     * Number is a high paid number.
     */
    @Override
    public boolean isValid(String number) {
        if (number == null) {
            return false;
        }
        if (number.length() >= 4 && number.length() <= 6) {
            if (number.startsWith("7")) {
                return true;
            }
            if (number.startsWith("9")) {
                return true;
            }
        }
        return false;
    }

}
