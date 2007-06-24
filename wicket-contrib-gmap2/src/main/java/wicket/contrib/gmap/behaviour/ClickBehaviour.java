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
package wicket.contrib.gmap.behaviour;

import java.util.List;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;

import wicket.contrib.gmap.GMap2Panel;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;

public class ClickBehaviour extends AbstractGMapBehaviour
{
	private static final long serialVersionUID = 1L;

	private GMap2Panel gmap2Panel;

	public ClickBehaviour(GMap2Panel gmap2Panel)
	{
		this.gmap2Panel = gmap2Panel;
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		Request request = RequestCycle.get().getRequest();
		String markerString = request.getParameter("marker");
		if ("".equals(markerString))
		{
			GLatLng gLatLng = convertToGLatLng(request.getParameter("point"));
			gmap2Panel.onClick(gLatLng, target);
		}
		else
		{
			int hashCode = Integer.parseInt(markerString);
			List<GMarker> markers = (List<GMarker>)gmap2Panel.getModelObject();
			for (GMarker marker : markers)
			{
				if (marker.hashCode() == hashCode)
				{
					marker.onClick(target);
					break;
				}
			}
		}
		
	}
}