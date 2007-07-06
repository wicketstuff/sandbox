/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
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
package wicket.contrib.gmap.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

import wicket.contrib.gmap.GMap2;

/**
 * TODO should we put 'infoWindowOpen', 'infoWindowBeforeClose' and 'infoWindowClose'
 * together in this listener?
 */
public abstract class InfoWindowListener extends GMap2.ListenerBehavior
{

	@Override
	protected String getJSmethod() {
		return "addInfoWindowListener";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target)
	{
		// TODO decide between infoWindowOpen/infoWindowBeforeClose/infoWindowClose
		onInfoWindowClose(target);
	}

	protected void onInfoWindowOpen(AjaxRequestTarget target)
    {
	}
    
	protected void onInfoWindowBeforeClose(AjaxRequestTarget target)
    {
	}
	
	protected void onInfoWindowClose(AjaxRequestTarget target)
    {
	}
}