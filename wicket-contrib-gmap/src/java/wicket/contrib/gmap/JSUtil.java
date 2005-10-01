package wicket.contrib.gmap;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class JSUtil {

    public static String createFunction(String name, String body) {
        return "function " + name + "() {\n" + body + "\n}";
    }

    public static String longitudeAsString(GPoint point) {
        return Float.toString(point.getLongitude()).replace(".", "").replace("-", "1");
    }

    public static String latitudeAsString(GPoint point) {
        return Float.toString(point.getLatitude()).replace(".", "").replace("-", "1");
    }

    public static String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
