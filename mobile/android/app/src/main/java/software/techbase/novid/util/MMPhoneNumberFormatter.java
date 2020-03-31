package software.techbase.novid.util;

/**
 * Created by Wai Yan on 10/17/18.
 */
public final class MMPhoneNumberFormatter {

    public static String format(String phoneNumber) {

        if (phoneNumber.startsWith("09") && phoneNumber.length() >= 9) {
            return "+95" + phoneNumber.substring(1);
        }
        return phoneNumber;
    }
}
