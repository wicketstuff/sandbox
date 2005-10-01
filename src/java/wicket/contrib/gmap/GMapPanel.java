package wicket.contrib.gmap;

import wicket.AttributeModifier;
import wicket.markup.html.WebComponent;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class GMapPanel extends Panel {

    /**
     * @param id   panel id
     * @param gmap
     */
    public GMapPanel(String id, GMap gmap) {
        this(id, gmap, 400, 300, LOCALHOST_KEY);
    }

    /**
     * @param id
     * @param gmap
     * @param width
     * @param height
     */
    public GMapPanel(String id, GMap gmap, int width, int height) {
        this(id, gmap, width, height, LOCALHOST_KEY);
    }

    /**
     * @param id
     * @param gmap    gmap object
     * @param width   map width in px
     * @param height  map height in px
     * @param gmapKey key generated for your site, you can get it from <a href="http://www.google.com/apis/maps/signup.html">here</a>
     */
    public GMapPanel(String id, GMap gmap, int width, int height, String gmapKey) {
        super(id);

        add(new GMapReference("jsReference", GMAP_URL + gmapKey));
        add(new GMapContainer("gMapContainer", gmap));

        WebComponent mapDiv = new WebComponent("map");
        mapDiv.add(new AttributeModifier("style", new Model("width: " + width + "px; height: " + height + "px")));
        add(mapDiv);
    }

    // gmap url
    private static final String GMAP_URL = "http://maps.google.com/maps?file=api&v=1&key=";

    // default gmap key for http://localhost/
    private static final String LOCALHOST_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxRTqfd1PEwgWtnBwhFCBpkPDmu-nA";
}
