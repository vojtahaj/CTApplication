package umte.fim.uhk.cz.ctapplication.utils;

import java.util.regex.Pattern;

public class IPValidator {

    private static final Pattern PATTERN = Pattern.compile(
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean isIPAddress(final String ip) {
        return !PATTERN.matcher(ip).matches();
    }

    public static boolean isPort(final String port) {
        try {
            Integer.parseInt(port);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
}
