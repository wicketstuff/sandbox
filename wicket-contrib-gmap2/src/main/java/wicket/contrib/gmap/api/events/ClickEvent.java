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
