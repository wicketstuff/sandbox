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

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

import wicket.contrib.gmap.GMap2;

public abstract class GEventListener extends AbstractDefaultAjaxBehavior
{	
	private static final long serialVersionUID = 1L;

	protected void onBind()
	{
		if (!(getComponent() instanceof GMap2)) {
			throw new IllegalArgumentException("must be bound to GMap2");
		}
	}
	
	public String getJSadd()
	{
		return getGMap2().getJSinvoke("addListener('" + getEvent() + "', '" + getCallbackUrl() + "')");
	}
	
	protected abstract String getEvent();
	
	protected final GMap2 getGMap2() {
		return (GMap2)getComponent();
	}
	
	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		getGMap2().update(target);
		
		onEvent(target);
	}

	protected abstract void onEvent(AjaxRequestTarget target);
}