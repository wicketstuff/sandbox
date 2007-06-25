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

import java.util.Locale;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.convert.converters.IntegerConverter;

import wicket.contrib.gmap.GMap2Panel;

public class MoveEndBehaviour extends AbstractGMapBehaviour
{

	private static final long serialVersionUID = 1L;

	private GMap2Panel gmap2Panel;

	public MoveEndBehaviour(GMap2Panel gmap2Panel)
	{
		this.gmap2Panel = gmap2Panel;
	}

	/*
	 * TODO update bounds
	 */
	@Override
	protected void respond(AjaxRequestTarget target)
	{
		Request request = RequestCycle.get().getRequest();
		gmap2Panel.setCenter(convertToGLatLng(request.getParameter("center")));
		gmap2Panel.setZoomLevel((Integer)IntegerConverter.INSTANCE.convertToObject(request
				.getParameter("zoom"), Locale.getDefault()));
		gmap2Panel.onMoveEnd(target);
	}
}
