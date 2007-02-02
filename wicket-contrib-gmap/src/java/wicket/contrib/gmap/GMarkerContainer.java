/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.gmap;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.markup.html.WebMarkupContainer;

/**
 * Wicket container for Google's GMarker API. It contains two components:
 * <ul>
 * <li> gmarker component</li>
 * <li> user's info component </li>
 * </ul>
 * and only one of them can be visible in the same time. At very first time,
 * page rendering time, gmarker component is rendered, the other one is hidden.
 * 
 * @author Iulian-Corneliu Costan
 * @see GMapAjaxBehavior
 */
class GMarkerContainer extends WebMarkupContainer
{

	private static final long serialVersionUID = 1L;

	private GMapAjaxBehavior behaviour;

	// todo quick hack
	private String markupId;

	public GMarkerContainer(MarkupContainer parent, GMarker gmarker)
	{
		super(parent, ID);
		this.markupId = gmarker.getOverlayId();

		// todo name constraint
		Component infoComponent = gmarker.getComponent(this, INFO_COMPONENT_ID);
		infoComponent.setVisible(false);

		behaviour = new GMapAjaxBehavior();
		add(behaviour);

		new GMarkerComponent(this, gmarker, behaviour);
	}

	public void toggleVisibility()
	{
		Component component = get(GMarkerComponent.ID);
		component.setVisible(false);

		component = get(INFO_COMPONENT_ID);
		component.setVisible(true);
	}

	/**
	 * Return the DOM Id of the component that has to be updated.
	 */
	public String getMarkupId()
	{
		return markupId;
	}

	public static final String ID = "gmarkerContainer";
	private static final String INFO_COMPONENT_ID = "gmarkerInfo";
}
