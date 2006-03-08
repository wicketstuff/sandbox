package wicket.contrib.gmap;

import wicket.behavior.AbstractAjaxBehavior;
import wicket.markup.html.PackageResourceReference;

/**
 * Wicket component for Google's GMarker API.
 * It generates GMarker's JavaScript logic (event handling, info window) that will initiate an AJAX request when gmarker is clicked.
 *
 * @author Iulian-Corneliu Costan
 */
class GMarkerComponent extends JavaScriptComponent
{

    private AbstractAjaxBehavior behavior;
    private GMarker gmarker;

    private static final PackageResourceReference ref = new PackageResourceReference(GMapPanel.class,
            GMapInitializer.INDICATOR);

    public GMarkerComponent(GMarker gmarker, AbstractAjaxBehavior behavior)
    {
        super(ID);
        this.gmarker = gmarker;
        this.behavior = behavior;
    }

    public String onJavaScriptComponentTagBody()
    {
        StringBuffer buffer = new StringBuffer("\n");
        buffer.append(createMarker() + "\n");
        buffer.append(createInfoFunction() + "\n");
        return buffer.toString();
    }

    private String createMarker()
    {
        return JSUtil.createFunction(gmarker.getFactoryMethod(),
                "var marker = new GMarker(" + gmarker.getPointAsString() + ");" + "\n" +
                        getOnClickHandler() + "\n" +
                        "GEvent.addListener(marker, \"click\", onClick);" + "\n" +
                        "return marker;");
    }

    private String getOnClickHandler()
    {
        return JSUtil.createFunction("onClick", "marker.openInfoWindow(" + getInfoFactoryName() + "());");
    }

    private String createInfoFunction()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("var div = document.createElement(\"div\");\n");
        buffer.append("div.id = \"" + gmarker.getOverlayId() + "\";\n");
        buffer.append("var progress = document.createElement(\"img\");\n");
        buffer.append("progress.src = \"" + getPage().urlFor(ref) + "\";\n");
        buffer.append("var waitText = document.createTextNode(\"Please wait ...\");\n");
        buffer.append("var nobr = document.createElement(\"nobr\");\n");
        buffer.append("nobr.appendChild(progress);\n");
        buffer.append("nobr.appendChild(waitText);\n");
        buffer.append("div.appendChild(nobr);\n");
        buffer.append("var script = document.createElement(\"script\");\n");
        buffer.append("var js = document.createTextNode(\"");
        buffer.append("dojoCall('" + behavior.getCallbackUrl() + "','" + gmarker.getOverlayId() + "');\")\n");
        buffer.append("script.appendChild(js);\n");
        buffer.append("div.appendChild(script);\n");
        buffer.append("return div;");
        return JSUtil.createFunction(getInfoFactoryName(), buffer.toString());
    }

    //todo implement empty
    private String createEmptyMarker()
    {
        return JSUtil.createFunction(gmarker.getFactoryMethod(), "return new GMarker(" + gmarker.toString() + ");");
    }

    private String getInfoFactoryName()
    {
        return "createInfo" + gmarker.getOverlayId();
    }

    public static final String ID = "gmarkerComponent";
}
