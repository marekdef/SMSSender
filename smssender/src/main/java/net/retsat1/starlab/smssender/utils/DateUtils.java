package net.retsat1.starlab.smssender.utils;

public class DateUtils {

    private static final long NUMBER_OF_SEC_FOR_MIN = 60;
    private static final long NUMBER_OF_SEC_FOR_HOUR = 60 * 60;
    private static final long NUMBER_OF_SEC_FOR_DAY = 60 * 60 * 24;
    private static final long NUMBER_OF_SEC_FOR_YEAR = 60 * 60 * 24 * 365;
    private static final String STRING_SECOUND = "";
    private static final String STRING_MINUTE = ":";
    private static final String STRING_HOUR = ":";
    private static final String STRING_DAY = "d ";
    private static final String STRING_YEAR = "y ";

    public static String changeSecToNiceDate(long time) {
        if (time < NUMBER_OF_SEC_FOR_MIN) {
            int sec = getSec(time);
            return formatTo2Digits(sec);
        } else if (time < NUMBER_OF_SEC_FOR_HOUR) {
            int min = getMin(time);
            int sec = getSec(time);
            return formatTo2Digits(min) + STRING_MINUTE + formatTo2Digits(sec) + STRING_SECOUND;
        } else if (time < NUMBER_OF_SEC_FOR_DAY) {
            int h = getHour(time);
            int min = getMin(time);
            int sec = getSec(time);
            return formatTo2Digits(h) + STRING_HOUR + formatTo2Digits(min) + STRING_MINUTE + formatTo2Digits(sec) + STRING_SECOUND;
        } else if (time < NUMBER_OF_SEC_FOR_YEAR) {
            int d = getDay(time);
            int h = getHour(time);
            int min = getMin(time);
            int sec = getSec(time);
            return d + STRING_DAY + h + STRING_HOUR + min + STRING_MINUTE + sec + STRING_SECOUND;
        } else {
            int y = getYear(time);
            int d = getDay(time);
            int h = getHour(time);
            int min = getMin(time);
            int sec = getSec(time);
            return y + STRING_YEAR + d + STRING_DAY + h + STRING_HOUR + min + STRING_MINUTE + sec + STRING_SECOUND;
        }
    }

    private static String formatTo2Digits(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return number + "";
    }

    private static int getYear(long time) {
        return (int) (time / NUMBER_OF_SEC_FOR_YEAR);
    }

    private static int getDay(long time) {

        return (int) ((time / NUMBER_OF_SEC_FOR_DAY) % 365);
    }

    private static int getHour(long time) {
        return (int) ((time / NUMBER_OF_SEC_FOR_HOUR) % 24);
    }

    private static int getMin(long time) {
        return (int) ((time / NUMBER_OF_SEC_FOR_MIN) % 60);
    }

    private static int getSec(long time) {
        return (int) (time % NUMBER_OF_SEC_FOR_MIN);

    }

}
