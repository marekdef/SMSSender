package net.retsat1.starlab.smssender.validators;

public interface NumberValidator {

    /**
     * When number is valid return true, if number has mistakes, return false
     * 
     * @param number
     * @return
     */
    public boolean isValid(String number);

    /**
     * Get id of android string resource. This function support i18n.
     * 
     * @return
     */
    public int getErrorMessageRef();
}
