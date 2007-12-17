/*
 * $Id: GMapPanel.java 695 2006-04-26 21:14:54Z syca $ $Revision: 695 $ $Date:
 * 2006-02-12 22:46:53 +0200 (Sun, 12 Feb 2006) $
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.gmap;

import org.apache.wicket.markup.html.form.persistence.IValuePersister;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * A reusable wicket component for <a href="http://maps.google.com">Google Maps</a>.
 * Becasue Maps API requires a different key for each deployment context you
 * have to either generate a new key (check <a
 * href="http://www.google.com/apis/maps/signup.html">Google Maps API - Sign Up</a>
 * for more info) or use already generated ones:
 * {@link GMapPanel.GMAP_DEFAULT_KEY} or {@link GMapPanel.GMAP_8080_KEY}
 * 
 * @see GMap
 * 
 * @author Iulian-Corneliu Costan
 */
public class GMapPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a GMapPanel with width=400, height=300 and using default
	 * {GMapPanel.GMAP_DEFAULT_KEY} key. Make sure that deployment context of
	 * your application is <a
	 * href="http://localhost/gmap/">http://localhost/gmap/</a>
	 * 
	 * @param id
	 *            wicket component id
	 * @param gmap
	 *            a GMap instance
	 */
	public GMapPanel(String id, GMap gmap)
	{
		this(id, gmap, 400, 300, GMAP_DEFAULT_KEY);
	}

	/**
	 * Creates GMapPanel component using default
	 * {@link GMapPanel.GMAP_DEFAULT_KEY} key. Make sure that deployment context
	 * of your application is <a
	 * href="http://localhost/gmap">http://localhost/gmap/</a>
	 * 
	 * @param id
	 *            wicket component id
	 * @param gmap
	 *            a GMap instance
	 * @param width
	 *            map width in px
	 * @param height
	 *            map height in px
	 */
	public GMapPanel(String id, GMap gmap, int width, int height)
	{
		this(id, gmap, width, height, GMAP_DEFAULT_KEY);
	}

	/**
	 * Create GMapPanel.
	 * 
	 * @param id
	 *            wicket component id
	 * @param gmap
	 *            a GMap object
	 * @param width
	 *            map width in px
	 * @param height
	 *            map height in px
	 * @param gmapKey
	 *            key generated for your site, you can get it from <a
	 *            href="http://www.google.com/apis/maps/signup.html">here</a>
	 */
	public GMapPanel(String id, GMap gmap, int width, int height, String gmapKey)
	{
		super(id);

		add(new GMapScript("script", GMAP_URL + gmapKey));
		add(new GMapInitializer(gmap));
		add(new GMapContainer(gmap));
		add(new Map("map", width, height));
	}

	/**
	 * @param clickListener
	 */
	public void addClickListener(GMapClickListener clickListener)
	{
		add(new GMapClickEventBehavior(clickListener));
	}

	// gmap url
	private static final String GMAP_URL = "http://maps.google.com/maps?file=api&v=2&key=";

	/**
	 * GMap key for root context <a href="http://localhost/">http://localhost</a>
	 */
	public static final String LOCALHOST_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxRTqfd1PEwgWtnBwhFCBpkPDmu-nA";

	/**
	 * GMap key for <a
	 * href="http://localhost:8080/gmap">http://localhost:8080/gmap</a>
	 */
	public static final String GMAP_8080_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxTh_sjBSLCHIDZfjzu1cFb3Pz7MrRQLOeA7BMLtPnXOjHn46gG11m_VFg";

	/**
	 * Defaul GMap key for <a href="http://localhost/gmap">http://localhost/gmap</a>
	 */
	public static final String GMAP_DEFAULT_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxTIqKwA3nrz2BTziwZcGRDeDRNmMxS-FtSv7KGpE1A21EJiYSIibc-oEA";
}
