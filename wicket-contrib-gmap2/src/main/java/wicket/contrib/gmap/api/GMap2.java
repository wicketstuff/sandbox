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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import wicket.contrib.gmap.GMap2Panel;


/**
 * This class represents the main Maps API's GMap2 object. <a
 * href="http://www.google.com/apis/maps/documentation/reference.html#GMap2">GMap2</a>
 */
public class GMap2 implements Serializable
{
	private static final long serialVersionUID = 1L;

	private GMap2Panel gMap2Panel;

	private GLatLng center;
	private int zoomLevel;
	private List<GOverlay> overlays = new ArrayList<GOverlay>();
	private Set<GControl> controls = new HashSet<GControl>();

	/**
	 * Create GMap component
	 */
	public GMap2()
	{
		this(new GLatLng(37.4419, -122.1419), 13);
	}

	/**
	 * Create GMap component
	 * 
	 * @param center
	 *            of the gmap
	 * @param zoomLevel
	 *            only values between 1 and 17 are allowed.
	 */
	public GMap2(GLatLng center, int zoomLevel)
	{
		if (center == null)
		{
			throw new IllegalArgumentException("map's center point cannot be null");
		}
		if (zoomLevel < 0 || zoomLevel > 17)
		{
			throw new IllegalArgumentException("zoomLevel must be 1 < zoomLevel < 17 ");
		}
		this.center = center;
		this.zoomLevel = zoomLevel;
	}

	/**
	 * Add a overlay to this map.
	 * 
	 * @see GOverlay
	 * @param overlay
	 */
	public void addOverlay(GOverlay overlay)
	{
		overlay.setGMap2(this);
		overlays.add(overlay);
	}
	
    /**
     * Remove an overlay from this map.
     *
     * @see GOverlay
     * @param overlay
     */
    public void removeOverlay(GOverlay overlay)
    {
        overlays.remove(overlay);
    } 

	/**
	 * Gets a unmodifyable List of all overlays.
	 * 
	 * @return overlays
	 */
	public List<GOverlay> getOverlays()
	{
		return Collections.unmodifiableList(overlays);
	}

	public void addControl(GControl control)
	{
		controls.add(control);
	}

	public Set<GControl> getControls()
	{
		return Collections.unmodifiableSet(controls);
	}

	public GLatLng getCenter()
	{
		return center;
	}

	public int getZoomLevel()
	{
		return zoomLevel;
	}

	public void setZoomLevel(int parameter)
	{
		this.zoomLevel = parameter;
	}

	public void setCenter(GLatLng center)
	{
		this.center = center;
	}

	public GMap2Panel getGMap2Panel()
	{
		return gMap2Panel;
	}

	public void setGMap2Panel(GMap2Panel panel)
	{
		this.gMap2Panel = panel;
	}
}
