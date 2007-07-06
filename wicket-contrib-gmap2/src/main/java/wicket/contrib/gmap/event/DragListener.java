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
 * TODO should we put 'dragstart', 'drag' and 'dragend' together in this listener?
 */
public abstract class DragListener extends GMap2.ListenerBehavior
{

	@Override
	protected String getJSmethod() {
		return "addDragListener";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target)
	{
		// TODO decide between dragStart/drag/dragEnd
		onDragEnd(target);
	}

	protected void onDragStart(AjaxRequestTarget target)
	{
	}
	
	protected void onDrag(AjaxRequestTarget target)
	{
	}
	
	protected void onDragEnd(AjaxRequestTarget target)
	{
	}
}