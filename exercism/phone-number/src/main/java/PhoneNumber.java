import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhoneNumber {

    private static final Pattern SCHEME
            = Pattern.compile("1?\\(?(\\p{Digit}{3})[). ]*(\\p{Digit}{3})[-.]?(\\p{Digit}{4})");

    private String areaCode = "000";
    private String prefix = "000";
    private String lineNumber = "0000";

    public PhoneNumber(String number) {
        Matcher matcher = SCHEME.matcher(number);
        if (matcher.matches()) {
            areaCode = matcher.group(1);
            prefix = matcher.group(2);
            lineNumber = matcher.group(3);
        }
    }

    public String getNumber() { return areaCode + prefix + lineNumber; }

    public String getAreaCode() { return areaCode; }

    public String pretty() { return "(" + areaCode + ") " + prefix + '-' + lineNumber; }
}