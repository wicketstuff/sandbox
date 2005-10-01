package wicket.contrib.gmap;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class GPoint extends JSComponent {

    private float longitude;
    private float latitude;

    public GPoint(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    protected String toJavaScript() {
        return "new GPoint(" + longitude + ", " + latitude + ")";
    }
}
