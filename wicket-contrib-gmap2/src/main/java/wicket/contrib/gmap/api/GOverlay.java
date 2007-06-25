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
 * Represents an Google Maps API's GOverlay. <a
 * href="http://www.google.com/apis/maps/documentation/reference.html#GOverlay">GOverlay</a>
 * 
 */
public abstract class GOverlay implements GMapApi
{	
	public String getIdentifier() {
		return "" + System.identityHashCode(this);
	}
	
	@Override
	public int hashCode()
	{
		return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof GOverlay)
		{
			GOverlay overlay = (GOverlay)obj;
			return overlay.getIdentifier().equals(getIdentifier());
		}
		return false;
	}
}
