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
package wicket.contrib.examples.gmap;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap;
import wicket.contrib.gmap.GMapPanel;
import wicket.contrib.gmap.GMarker;
import wicket.contrib.gmap.GPoint;
import wicket.markup.html.basic.Label;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class HomePage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;

	public HomePage() {
		// add gmap
		GMap gmap = new GMap(new GPoint(10, 30), 15);
		gmap.setTypeControl(true);
		gmap.setSmallMapControl(true);

		// www.wicket-library.com
		GMarker wicketLibrary = new GMarker(new GPoint(-112.1872f, 33.2765f),
				new Label(this, "gmarkerInfo", "www.wicket-library.com"));
		gmap.addOverlay(wicketLibrary);

		// www.wicket.org
		GMarker wicket = new GMarker(new GPoint(-78.7073f, 35.7512f),
				new InfoPanel(this, "gmarkerInfo"));
		gmap.addOverlay(wicket);

		new GMapPanel(this, "gmap", gmap, 800, 600,
				LOCALHOST_8080_WICKET_CONTRIB_GMAP_KEY);
	}

	// pay attention at webapp deploy context, we need a different key for each
	// deploy context
	// check <a href="http://www.google.com/apis/maps/signup.html">Google Maps
	// API - Sign Up</a> for more info

	// key for http://localhost:8080/wicket-contrib-gmap, deploy context is
	// wicket-contrib-gmap
	private static final String LOCALHOST_8080_WICKET_CONTRIB_GMAP_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxTDxbH1TVfo7w-iwzG2OxhXSIjJdhQTwgha-mCK8wiVEq4rgi9qvz8HYw";

	// key for http://localhost:8080/gmap, deploy context is gmap
	private static final String GMAP_8080_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxTh_sjBSLCHIDZfjzu1cFb3Pz7MrRQLOeA7BMLtPnXOjHn46gG11m_VFg";

	// key for http://localhost/gmap
	private static final String GMAP_DEFAULT_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxTIqKwA3nrz2BTziwZcGRDeDRNmMxS-FtSv7KGpE1A21EJiYSIibc-oEA";

	// key for http://www.wicket-library.com/wicket-examples/
	private static final String WICKET_LIBRARY_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxQTV35WN9IbLCS5__wznwqtm2prcBQxH8xw59T_NZJ3NCsDSwdTwHTrhg";
}
