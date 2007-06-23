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
 * Represents an Google Maps API's GMarker
 * <a href="http://www.google.com/apis/maps/documentation/reference.html#GMarker">GMarker</a>
 *
 */
public class GMarker extends GOverlay
{
	private static final long serialVersionUID = 1L;

	private GLatLng point;
	
	private String title;

	/**
	 * @param point
	 *            the point on the map where this marker will be anchored
	 */
	public GMarker(GLatLng point)
	{
		this(point, null);
	}
	
	public GMarker(GLatLng point, String title)
	{
		super();
		
		this.point = point;
		this.title = title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String getJSConstructor()
	{
		return "new GMarker(" + point.getJSConstructor() + ",{" + getOptions() + "})";
	}

	public GLatLng getLagLng() {
		return point;
	}
	
	private String getOptions() {
		StringBuffer options = new StringBuffer();
		
		if (title != null) {
			options.append("title: \"");
			options.append(title);
			options.append("\"");
		}
		
		return options.toString();
	}
}
