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
package wicket.contrib.gmap.api;

import wicket.contrib.gmap.js.ObjectLiteral;

public class GMarkerOptions implements GValue
{
	private String title;
	
	private boolean clickable = true;
	
	private boolean draggable = false;
	
	private boolean bouncy = true;
	
	private boolean autoPan = false;
	
	private GIcon icon = null;

	public String getJSconstructor()
	{
		ObjectLiteral literal = new ObjectLiteral();

		if (title != null)
		{
			literal.set("title", "\"" + title + "\"");
		}
		if (!clickable)
		{
			literal.set("clickable", "false");
		}
		if (draggable)
		{
			literal.set("draggable", "true");
		}
		if (!bouncy)
		{
			literal.set("bouncy", "false");
		}
		if (autoPan)
		{
			literal.set("autoPan", "true");
		}
		if(icon != null)
		{
			literal.set("icon", icon.getId());
		}

		return literal.toString();
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public boolean isBouncy() {
		return bouncy;
	}

	public void setBouncy(boolean bouncy) {
		this.bouncy = bouncy;
	}

	public boolean isAutoPan() {
		return autoPan;
	}

	public void setAutoPan(boolean autoPan) {
		this.autoPan = autoPan;
	}

	public void setIcon(GIcon icon)
	{
		this.icon = icon;
	}
	
	public GIcon getIcon()
	{
		return icon;
	}
}
