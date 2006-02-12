/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.gmap;

import wicket.markup.html.panel.Panel;

/**
 * A reusable wicket component for <a href="http://local.google.com">Google MAPS</a>.
 *
 * @author Iulian-Corneliu Costan
 */
public class GMapPanel extends Panel
{

    /**
     * @param id   wicket component id
     * @param gmap a GMap instance
     */
    public GMapPanel(String id, GMap gmap)
    {
        this(id, gmap, 400, 300, LOCALHOST_KEY);
    }

    /**
     * @param id     wicket component id
     * @param gmap   a GMap instance
     * @param width  map width in px
     * @param height map height in px
     */
    public GMapPanel(String id, GMap gmap, int width, int height)
    {
        this(id, gmap, width, height, LOCALHOST_KEY);
    }

    /**
     * @param id      wicket component id
     * @param gmap    a GMap instance
     * @param width   map width in px
     * @param height  map height in px
     * @param gmapKey key generated for your site, you can get it from <a href="http://www.google.com/apis/maps/signup.html">here</a>
     */
    public GMapPanel(String id, GMap gmap, int width, int height, String gmapKey)
    {
        super(id);

        add(new GMapScript("script", GMAP_URL + gmapKey));
        add(new GMapContainer(gmap));
        add(new Map("map", width, height));
    }

    // gmap url
    private static final String GMAP_URL = "http://maps.google.com/maps?file=api&v=1&key=";

    // default gmap key for http://localhost/
    private static final String LOCALHOST_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxRTqfd1PEwgWtnBwhFCBpkPDmu-nA";
}
