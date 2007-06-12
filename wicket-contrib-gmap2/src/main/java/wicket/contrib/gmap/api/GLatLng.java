package wicket.contrib.gmap.api;

import java.io.Serializable;

/**
 * Represents an Google Maps API's GLatLng.
 * <a href="http://www.google.com/apis/maps/documentation/reference.html#GLatLng">GLatLng</a>
 *
 */
public class GLatLng extends GMapApi implements Serializable
{
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private final double lat;
    private final double lng;
    private final boolean unbounded;

    /**
     * Construct.
     * @param lat
     * @param lng
     */
    public GLatLng(double lat, double lng)
    {
    	this(lat, lng, false);
    }

    /**
     * Construct.
     * @param lat
     * @param lng
     * @param unbounded
     */
    public GLatLng(double lat, double lng, boolean unbounded)
    {
        this.lat = lat;
        this.lng = lng;
        this.unbounded = unbounded;
    }

    public double getLat()
    {
        return lat;
    }

    public double getLng()
    {
        return lng;
    }

    public String toString()
    {
        return getJSConstructor();
    }

	@Override
	public String getJSConstructor()
	{
        return "new GLatLng(" + lat + ", " + lng + ", " + unbounded + ")";
	}
}
