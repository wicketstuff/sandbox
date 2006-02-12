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
import wicket.markup.html.WebMarkupContainer;

/**
 * Wicket container for Google's GMarker API. It contains two components:
 * <ul>
 * <li> gmarker component</li>
 * <li> user's info component </li>
 * </ul>
 * and only one of them can be visible in the same time.
 * At very first time, page rendering time, gmarker component is rendered, the other one is hidden.
 *
 * @author Iulian-Corneliu Costan
 * @see GMapAjaxHandler
 */
class GMarkerContainer extends WebMarkupContainer
{

    private GMapAjaxHandler behaviour;

    public GMarkerContainer(GMarker gmarker)
    {
        super(ID);

        // todo name constraint
        Component infoComponent = gmarker.getComponent();
        if (INFO_COMPONENT_ID.equals(infoComponent.getId()))
        {
            infoComponent.setVisible(false);
        }
        else
        {
            throw new IllegalArgumentException("the ID of your component has to be \"" + INFO_COMPONENT_ID + "\"");
        }

        behaviour = new GMapAjaxHandler();

        add(behaviour);
        add(new GMarkerComponent(gmarker, behaviour));
        add(infoComponent);
    }

    public void toggleVisibility()
    {
        get(GMarkerComponent.ID).setVisible(false);
        get(INFO_COMPONENT_ID).setVisible(true);
    }

    public static final String ID = "gmarkerContainer";
    private static final String INFO_COMPONENT_ID = "gmarkerInfo";
}
