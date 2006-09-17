/*
 * $Id: FXFeedbackTooltip.java 460 2005-11-28 21:51:03 -0800 (Mon, 28 Nov 2005)
 * eelco12 $ $Revision$ $Date: 2005-11-28 21:51:03 -0800 (Mon, 28 Nov
 * 2005) $
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
package wicket.contrib.markup.html.form.validation;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.contrib.markup.html.tooltip.Tooltip;
import wicket.contrib.markup.html.tooltip.TooltipPanel;
import wicket.markup.html.form.FormComponent;


/**
 * Tooltip class used by FXFeedbackIndicator. This class can also be used to
 * create a Tooltip which displays errormessages for a chosen formcomponent.
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 * 
 */
public class FXFeedbackTooltip extends TooltipPanel
{
	/**
	 * @param parent
	 * @param target
	 *            target Component
	 * @see Tooltip
	 */
	public FXFeedbackTooltip(MarkupContainer parent, Component target)
	{
		super(parent, target, 10, 20);

	}


	/**
	 * @param parent
	 * @see Tooltip constructor
	 * 
	 * @param target
	 *            target Component
	 * @param x
	 *            x offset
	 * @param y
	 *            y offset
	 */
	public FXFeedbackTooltip(MarkupContainer parent, Component target, int x, int y)
	{
		super(parent, target, x, y);

	}

	/**
	 * TODO docme!
	 * 
	 * @param component
	 */
	public void setComponentToCheck(Component component)
	{
		final FXTooltipFeedbackPanel feedback = new FXTooltipFeedbackPanel(this, "feedbackpanel",
				(FormComponent)component);
	}

}
