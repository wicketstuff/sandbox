/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.gmap.api;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxRequestTarget.IJavascriptResponse;

/**
 * Represents an Google Maps API's GMarker
 * <a href="http://www.google.com/apis/maps/documentation/reference.html#GMarker">GMarker</a>
 *
 */
public class GMarker<T extends Component> extends GOverlay
{
	private static final long serialVersionUID = 1L;

	private GLatLng point;

	/**
	 * @param point
	 *            the point on the map where this marker will be anchored
	 */
	public GMarker(GLatLng point)
	{
		super();
		this.point = point;
	}

	@Override
	public String getJSConstructor()
	{
		return "new GMarker(" + point.getJSConstructor() + ")";
	}

	/**
	 * Provides an AjaxRequestTarget.IListener providing JavaScript code to call the
	 * addOverlay method on the GMap. This is typically used by Listeners on the
	 * client programmers side to be able to add this GMarker as an Overlay within
	 * a AjaxCallCycle.
	 */
	public AjaxRequestTarget.IListener getJSAdd()
	{
		return new AjaxRequestTarget.IListener()
		{

			public void onBeforeRespond(Map map, AjaxRequestTarget target)
			{
			}

			public void onAfterRespond(Map map, IJavascriptResponse response)
			{
				response.addJavascript("addOverlay('"
						+ GMarker.this.getGMap2().getGMap2Panel().getMarkupId() + "', '"
						+ GMarker.this.hashCode() + "', '" + GMarker.this.getJSConstructor() + "')");
			}
		};
	}
}
