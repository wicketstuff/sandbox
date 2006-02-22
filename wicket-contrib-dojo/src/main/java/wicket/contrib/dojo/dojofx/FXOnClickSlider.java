/*
 * $Id$ $Revision$ $Date$
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
package wicket.contrib.dojo.dojofx;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.Response;
import wicket.model.Model;

/**
 * TODO HEY STUDENTS! didn't they tought you documentation is important? If
 * Martijn finds out, it'll cost you points.
 */
public class FXOnClickSlider extends DojoFXHandler
{

	private String HTMLID;
	private String componentId;
	private final int x;
	private final int y;
	private final String type;
	private int fromx;
	private int fromy;


	/**
	 * @param duration
	 * @param trigger
	 * @param x
	 * @param y
	 * @param relative
	 */
	public FXOnClickSlider(int duration, Component trigger, int x, int y, boolean relative)
	{
		super("onclick", duration, trigger);
		this.x = x;
		this.y = y;
		if (relative)
		{
			type = "relative";
		}
		else
		{
			type = "absolute";
		}

	}

	/**
	 * @param duration
	 * @param trigger
	 * @param x
	 * @param y
	 * @param fromx
	 * @param fromy
	 */
	public FXOnClickSlider(int duration, Component trigger, int x, int y, int fromx, int fromy)
	{
		super("onclick", duration, trigger);
		this.x = x;
		this.y = y;
		this.fromx = fromx;
		this.fromy = fromy;
		this.type = "fromto";

	}


	protected void onBind()
	{
		Component c = getComponent();
		this.component = (Component)c;
		this.componentId = c.getId();

		// create a unique HTML for the explode component
		HTMLID = this.component.getId() + "_" + component.getPath();
		// Add ID to component, and bind effect to trigger
		this.component.add(new AttributeModifier("id", true, new Model(HTMLID)));

		this.component.add(new AppendAttributeModifier("style", true, new Model(
				"position: absolute")));

		if (type == "relative")
		{
			this.getTrigger().add(
					new AppendAttributeModifier(getEventName(), true, new Model(
							"dojo.fx.html.slideBy(document.getElementById('" + HTMLID + "'), [" + x
									+ ", " + y + "]," + getDuration() + ")")));
		}
		else if (type == "fromto")
		{
			this.getTrigger().add(
					new AppendAttributeModifier(getEventName(), true, new Model(
							"dojo.fx.html.slide(document.getElementById('" + HTMLID + "'), ["
									+ fromx + ", " + fromy + "], [" + x + ", " + y + "],"
									+ getDuration() + ")")));
		}
		// assume that type == absolute
		else
		{
			this.getTrigger().add(
					new AppendAttributeModifier(getEventName(), true, new Model(
							"dojo.fx.html.slideTo(document.getElementById('" + HTMLID + "'), [" + x
									+ ", " + y + "]," + getDuration() + ")")));
		}

	}

	protected void onRenderHeadContribution(Response r)
	{
		// no header contributions necessary here.

	}
}