package wicket.contrib.gmap;

import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebMarkupContainer;


/**
 * @author Iulian-Corneliu COSTAN
 */
class GMapContainer extends WebMarkupContainer {

    private GMap gmap;

    GMapContainer(final String id, GMap gmap) {
        super(id);
        this.gmap = gmap;
    }

    protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        String body = "\n//<![CDATA[\n" +
                "\n" +
                "function initGMap() {\n" +
                "if (GBrowserIsCompatible()) {\n" +
                "\n" + gmap.toJavaScript() + "\n" +
                "}\n" +
                "}\n" +
                "\n" +
                "//]]>\n";
        replaceComponentTagBody(markupStream, openTag, body);
    }
}
