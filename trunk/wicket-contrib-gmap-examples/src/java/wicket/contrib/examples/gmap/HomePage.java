/*
 * $Id: HomePage.java 922 2006-09-07 17:52:05Z syca $
 * $Revision: 922 $
 * $Date: 2006-09-07 10:52:05 -0700 (Thu, 07 Sep 2006) $
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
import wicket.contrib.gmap.GMapClickListener;
import wicket.contrib.gmap.GMapPanel;
import wicket.contrib.gmap.GMarker;
import wicket.contrib.gmap.GLatLng;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class HomePage extends WicketExamplePage {
	private static final long serialVersionUID = 1L;

	public HomePage() {
		// add gmap
		GMap gmap = new GMap(new GLatLng(30, 10), 2);
		gmap.setTypeControl(true);
		gmap.setSmallMapControl(true);

		// www.wicket-library.com
		GMarker wicketLibrary = new GMarker(new GLatLng(33.2765f, -112.1872f),
				new Label("gmarkerInfo", "www.wicket-library.com"));
		gmap.addOverlay(wicketLibrary);

		// www.wicket.org
		GMarker wicket = new GMarker(new GLatLng(35.7512f, -78.7073f),
				new InfoPanel("gmarkerInfo"));
		gmap.addOverlay(wicket);

		GMapPanel mapPanel = new GMapPanel("gmap", gmap, 800, 600,
				LOCALHOST_8080_WICKET_CONTRIB_GMAP_EXAMPLES_KEY);
		mapPanel.addClickListener(new GMapClickListener() {

			public void onClick(AjaxRequestTarget target, GLatLng point) {
				System.out.println(point);
			}
		});
		add(mapPanel);
	}

	// pay attention at webapp deploy context, we need a different key for each
	// deploy context
	// check <a href="http://www.google.com/apis/maps/signup.html">Google Maps
	// API - Sign Up</a> for more info

	// key for http://localhost:8080/wicket-contrib-gmap-examples/
	private static final String LOCALHOST_8080_WICKET_CONTRIB_GMAP_EXAMPLES_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxTEpDPjw6LRH7yL06TcOjcEpKZmCRRGeXL1BMh_MNX22hDtswyQqVAOyQ";

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
