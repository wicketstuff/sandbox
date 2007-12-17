package wicket.contrib.gmap;

/**
 * @author Iulian-Corneliu Costan
 */
class GMapComponent extends JavaScriptComponent
{

    private GMap gmap;

    public GMapComponent(GMap gmap)
    {
        super(ID);
        this.gmap = gmap;
    }

    public String onJavaScriptComponentTagBody()
    {
        StringBuffer buffer = new StringBuffer("\n//<![CDATA[\n")
                .append("function initGMap() {\n")
                .append("if (GBrowserIsCompatible()) {\n")
                .append("\n" + gmapDefinition())
                .append("\n" + overlayDefinitions())
                .append("}\n")
                .append("}\n")
                .append("//]]>\n");
        return buffer.toString();
    }

    private String overlayDefinitions()
    {
        StringBuffer buffer = new StringBuffer();
        for (Overlay overlay : gmap.getOverlays())
        {
            buffer.append("map.addOverlay(" + overlay.getFactoryMethod() + "());\n");
        }
        return buffer.toString();
    }

    private String gmapDefinition()
    {
        StringBuffer buffer = new StringBuffer("var map = new GMap(document.getElementById(\"map\"));\n");
        if (gmap.isSmallMapControl())
        {
            buffer.append("map.addControl(new GSmallMapControl());\n");
        }
        if (gmap.isTypeControl())
        {
            buffer.append("map.addControl(new GMapTypeControl());\n");
        }
        buffer.append("map.centerAndZoom(").append(gmap.getCenter().toString())
                .append(", ").append(gmap.getZoomLevel()).append(");\n");
        return buffer.toString();
    }

    public static final String ID = "gmapComponent";
}
