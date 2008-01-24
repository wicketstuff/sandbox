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
import java.util.List;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.event.GOverlayListenerBehavior;

/**
 * Represents an Google Maps API's <a
 * href="http://www.google.com/apis/maps/documentation/reference.html#GOverlay">GOverlay</a>.
 */
public abstract class GOverlay implements Serializable
{
	List<GOverlayListenerBehavior> behaviors = new ArrayList<GOverlayListenerBehavior>();

	public GOverlay addBehavior(GOverlayListenerBehavior behavior)
	{
		behavior.setGOverlay(this);
		behaviors.add(behavior);

		return this;
	}

	public GOverlay removeBehavior(GOverlayListenerBehavior behavior)
	{
		while (behaviors.contains(behavior))
		{
			behaviors.remove(behavior);
		}

		behavior.setGOverlay(null);
		return this;
	}

	public GOverlay clearBehaviors()
	{
		behaviors.clear();

		return this;
	}

	public List<GOverlayListenerBehavior> getBehaviors()
	{
		return Collections.unmodifiableList(behaviors);
	}
	
	public String getJSadd(GMap2 map)
	{
		return map.getJSinvoke("addOverlay('" + getId() + "', "		
				+ getJSconstructor() + ")");
	}

	public String getJSremove(GMap2 map)
	{
		return map.getJSinvoke("removeOverlay('" + getId() + "')");
	}
	
	public String getId() {
		return String.valueOf(System.identityHashCode(this));
	}

	protected abstract String getJSconstructor();
}