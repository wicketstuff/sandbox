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
package wicket.contrib.gmap.api.events;

import java.io.Serializable;

import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;

/**
 */
public class ClickEvent extends GMap2Event implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GMarker marker;
	private GLatLng point;

	/**
	 * Construct.
	 * @param marker
	 * @param point
	 */
	public ClickEvent(GMarker marker, GLatLng point)
	{
		this.marker = marker;
		this.point  = point;
	}

	/**
	 * @return
	 */
	public GMarker getMarker()
	{
		return marker;
	}

	/**
	 * @return
	 */
	public GLatLng getPoint()
	{
		return point;
	}

}
