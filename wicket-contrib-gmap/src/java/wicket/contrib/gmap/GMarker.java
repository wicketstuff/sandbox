package wicket.contrib.gmap;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class GMarker extends Overlay {

    private GPoint point;
    private String message;

    public GMarker(GPoint point) {
        this.point = point;
    }

    public void setOnClickMessage(String message) {
        this.message = message;
    }

    protected String toJavaScript() {
        String marker = null;
        if (hasInfoWindow()) {
            marker = createMessageMarker();
        } else {
            marker = createSimpleMarker();
        }
        getContainer().define(marker);
        return getMarkerName() + "()";
    }

    private String createSimpleMarker() {
        return JSUtil.createFunction(getMarkerName(), "return new GMarker(" + point.toJavaScript() + ");");
    }

    private String createMessageMarker() {
        return JSUtil.createFunction(getMarkerName(),
                "var marker = new GMarker(" + point.toJavaScript() + ");" + "\n" +
                        getOnClickHandler() + "\n" +
                        "GEvent.addListener(marker, \"click\", onClick);" + "\n" +
                        "return marker;");
    }

    private String getOnClickHandler() {
        return JSUtil.createFunction("onClick", "marker.openInfoWindowHtml(\"" + JSUtil.escape(message) + "\");");
    }

    private String getMarkerName() {
        return "createMarker" + JSUtil.longitudeAsString(point) + JSUtil.latitudeAsString(point);
    }

    private boolean hasInfoWindow() {
        return message != null && !"".equals(message);
    }
}
