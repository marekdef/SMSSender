package net.retsat1.starlab.smssender.validators;

public class NumberHighPaidValidator implements NumberValidator {

    private static final int MIN_LENGTH_PAID_NUMBER = 4;
    private static final int MAX_LENGTH_PAID_NUMBER = 6;
    private static final String[] FIRST_PAID_DIGT = new String[] { "7", "9" };

    /**
     * Number is a high paid number.
     */
    @Override
    public boolean isValid(String number) {
        if (number == null) {
            return false;
        }
        if (number.length() >= MIN_LENGTH_PAID_NUMBER && number.length() <= MAX_LENGTH_PAID_NUMBER) {
            for (String digt : FIRST_PAID_DIGT) {
                if (number.startsWith(digt)) { //If number start from 7 or 9 then number is paid (length from 4 to 6)
                    return true;
                }
            }
        }
        return false;
    }

}
