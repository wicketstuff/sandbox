package wicket.contrib.gmap.api;

import java.io.Serializable;

/**
 * Represents an Google Maps API's GOverlay.
 * <a href="http://www.google.com/apis/maps/documentation/reference.html#GOverlay">GOverlay</a>
 *
 */
public abstract class GOverlay extends GMapApi implements Serializable
{
	private GMap2 gMap2;

	public GMap2 getGMap2()
	{
		return gMap2;
	}

	public void setGMap2(GMap2 gmap2)
	{
		this.gMap2 = gmap2;
	}
}
