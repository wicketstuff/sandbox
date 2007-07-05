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

/**
 * Represents an Google Maps API's 
 * <a href="http://www.google.com/apis/maps/documentation/reference.html#GPolygon">GPolygon</a>.
 */
public class GPolygon extends GOverlay
{
	private static final long serialVersionUID = 1L;

	private GLatLng[] gLatLngs;
	private String strokeColor;
	private int strokeWeight;
	private float strokeOpacity;
	private String fillColor;
	private float fillOpacity;

	public GPolygon(String strokeColor, int strokeWeight, float strokeOpacity, String fillColor, float fillOpacity, GLatLng... gLatLngs)
	{
		super();
		
		this.gLatLngs = gLatLngs;
		
		this.strokeColor = strokeColor;
		this.strokeWeight = strokeWeight;
		this.strokeOpacity = strokeOpacity;
		this.fillColor = fillColor;
		this.fillOpacity = fillOpacity;
	}

	public String getJSConstructor()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("new GPolygon([");

		boolean first = true;
		for (GLatLng gLatLng : gLatLngs) {
			if (!first) {
				buffer.append(",");
			}
			buffer.append(gLatLng.getJSConstructor());
			first = false;
		}
		
		buffer.append("],\"");
		buffer.append(strokeColor);
		buffer.append("\",");
		buffer.append(strokeWeight);
		buffer.append(",");
		buffer.append(strokeOpacity);
		buffer.append(",\"");
		buffer.append(fillColor);
		buffer.append("\",");
		buffer.append(fillOpacity);
		buffer.append(")");
		
		return buffer.toString();
	}
}
