package software.techbase.novid.util;

/**
 * Created by Wai Yan on 2019-06-06.
 */
public class MMNumberUtil {

    public static String convert(String value) {

        char[] array = value.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int eng : array) {

            switch (eng) {
                case 45:
                    sb.append('-');
                    break;
                case 46:
                    sb.append('.');
                    break;
                case 69:
                    sb.append('E');
                    break;
                default:
                    int bur = eng + 4112;
                    sb.append((char) bur);
            }
        }
        return sb.toString();
    }
}
