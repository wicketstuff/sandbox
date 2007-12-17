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
 * <a href="http://www.google.com/apis/maps/documentation/reference.html#GPolyline">GPolyline</a>.
 */
public class GPolyline extends GOverlay
{
	private static final long serialVersionUID = 1L;

	private GLatLng[] gLatLngs;
	private String color;
	private int weight;
	private float opacity;

	public GPolyline(String color, int weight, float opacity, GLatLng... gLatLngs)
	{
		super();
		
		this.gLatLngs = gLatLngs;
		this.color = color;
		this.weight = weight;
		this.opacity = opacity;
	}

	@Override
	protected String getJSconstructor()
	{
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("new GPolyline([");

		boolean first = true;
		for (GLatLng gLatLng : gLatLngs) {
			if (!first) {
				buffer.append(",");
			}
			buffer.append(gLatLng.getJSconstructor());
			first = false;
		}
		
		buffer.append("],\"");
		buffer.append(color);
		buffer.append("\",");
		buffer.append(weight);
		buffer.append(",");
		buffer.append(opacity);
		buffer.append(")");
		
		return buffer.toString();
	}
}
