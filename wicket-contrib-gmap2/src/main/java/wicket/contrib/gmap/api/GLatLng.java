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

import java.util.StringTokenizer;

/**
 * Represents an Google Maps API's GLatLng. <a
 * href="http://www.google.com/apis/maps/documentation/reference.html#GLatLng">GLatLng</a>
 * 
 */
public class GLatLng implements GMapApi
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
	 * 
	 * @param lat
	 * @param lng
	 */
	public GLatLng(double lat, double lng)
	{
		this(lat, lng, false);
	}

	/**
	 * Construct.
	 * 
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

	/**
	 * @see wicket.contrib.gmap.api.GMapApi#getJSConstructor()
	 */
	public String getJSConstructor()
	{
		return "new GLatLng(" + lat + ", " + lng + ", " + unbounded + ")";
	}

	@Override
	public int hashCode()
	{
		return new Double(lat).hashCode() ^ new Double(lng).hashCode()
				^ new Boolean(unbounded).hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof GLatLng)
		{
			GLatLng t = (GLatLng)obj;
			return t.lat == lat && t.lng == lng && t.unbounded == unbounded;
		}
		return false;
	}
	
	public static GLatLng fromString(String value)
	{
		StringTokenizer tokenizer;
		try
		{
			tokenizer = new StringTokenizer(value, "(, )");
		}
		catch (NullPointerException e)
		{
			return null;
		}
		if (tokenizer.countTokens() != 2)
		{
			return null;
		}
		float lat = Float.valueOf(tokenizer.nextToken());
		float lng = Float.valueOf(tokenizer.nextToken());
		return new GLatLng(lat, lng);
	}
}
