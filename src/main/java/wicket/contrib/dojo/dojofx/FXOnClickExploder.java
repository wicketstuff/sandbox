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
import wicket.markup.html.internal.HtmlHeaderContainer;
import wicket.model.Model;

/**
 * @author Ruud Booltink
 * @author Marco van de Haar
 * 
 */
public class FXOnClickExploder extends DojoFXHandler
{

	/**
	 * whether component starts exploded or imploded. true for exploded.
	 * default: false
	 */
	private final boolean startDisplay;
	/**
	 * HTML ID for component.
	 */
	private String HTMLID;
	/**
	 * local instance to component's id, replaces getComponent().getID()
	 */
	private String componentId;

	/**
	 * if explode from box: box contains values for x, y, width and height
	 */
	private Box box;

	/**
	 * component from where exploding animation should start could not find a
	 * better name.....
	 */
	private Component from;

	/**
	 * Constructor where trigger component is also the component to be exploded
	 * from
	 * 
	 * @param duration
	 * @param trigger
	 */
	public FXOnClickExploder(int duration, Component trigger)
	{
		super("onclick", duration, trigger);
		this.startDisplay = false;
	}

	/**
	 * constructor to let the component explode from given Box in stead of from
	 * the trigger.
	 * 
	 * @param duration
	 * @param trigger
	 * @param x
	 *            x value of corner of Box
	 * @param y
	 *            y value of corner of Box
	 * @param width
	 *            width of Box
	 * @param height
	 *            height of Box
	 */
	public FXOnClickExploder(int duration, Component trigger, int x, int y, int width, int height)
	{
		super("onclick", duration, trigger);
		this.startDisplay = false;
		box = new Box(x, y, width, height);

	}

	/**
	 * Constructor where an extra component is passed where component explodes
	 * from.
	 * 
	 * @param duration
	 * @param trigger
	 * @param from
	 *            Component from where the exploding component should explode
	 */
	public FXOnClickExploder(int duration, Component trigger, Component from)
	{
		super("onclick", duration, trigger);
		this.startDisplay = false;
		this.from = from;
	}

	/**
	 * @see wicket.AjaxHandler#renderHeadContribution(wicket.markup.html.internal.HtmlHeaderContainer)
	 */
	protected void renderHeadContribution(Response r)
	{
		String s;

		// if box has not been created: explode from trigger
		String functionEx;
		String functionIm;

		// see if either box or from are set, and render the correct javascritp
		// functions
		if (box != null)
		{
			String coords = "[" + box.getX() + ", " + box.getY() + ", " + box.getWidth() + ", "
					+ box.getHeight() + "]";
			functionEx = "dojo.fx.html.explodeFromBox(" + coords + ", end, duration, function(){"
					+ componentId + "_exploderstate=\"exploded\";});";
			functionIm = "dojo.fx.html.implodeToBox(end, " + coords + ", duration, function(){"
					+ componentId + "_exploderstate=\"imploded\";});";

		}
		else if (from != null)
		{
			functionEx = "dojo.fx.html.explode(document.getElementById('" + from.getId()
					+ "'), end, duration, function(){" + componentId
					+ "_exploderstate=\"exploded\";});";
			functionIm = "dojo.fx.html.implode(end, document.getElementById('" + from.getId()
					+ "'), duration, function(){" + componentId + "_exploderstate=\"imploded\";});";
		}
		else
		{

			functionEx = "dojo.fx.html.explode(start, end, duration, function(){" + componentId
					+ "_exploderstate=\"exploded\";});";
			functionIm = "dojo.fx.html.implode(end, start, duration, function(){" + componentId
					+ "_exploderstate=\"imploded\";});";


		}

		if (startDisplay)
		{
			s = "\t<script language=\"JavaScript\" type=\"text/javascript\">\n" + "\t"
					+ componentId + "_expoderstate = 'exploded'; \n";
		}
		else
		{
			s = "\t<script language=\"JavaScript\" type=\"text/javascript\">\n" + "\t"
					+ componentId + "_exploderstate = 'imploded'; \n";
		}
		s = s + "\tfunction " + componentId + "_plode(start, endid, duration)\n" + "\t{\n"
				+ "\t\tvar end = document.getElementById(endid);\n" + "\t\tif(" + componentId
				+ "_exploderstate==\"imploded\")\n" + "\t\t{\n" + "\t\t\t" + componentId
				+ "_exploderstate = \"exploding\";\n" + "\t\t\t" + functionEx + "\n"
				+ "\t\t} else if (" + componentId + "_exploderstate == \"exploded\")\n" + "\t\t{\n"
				+ "\t\t\t" + componentId + "_exploderstate = \"imploding\";\n" + "\t\t\t"
				+ functionIm + "\n" + "\t\t} else {\n" + "\t\t}\n"

				+ "\t}\n" + "\t</script>\n";

		r.write(s);

	}


	/**
	 * @see wicket.contrib.dojo.dojofx.DojoFXHandler#addTrigger(wicket.Component)
	 */
	public void addTrigger(Component c)
	{
		if (getComponent() == null)
		{
			throw new NullPointerException(
					"Component is null. Cannot add extra trigger before effect is bound to component!");
		}
		c.add(new AppendAttributeModifier(getEventName(), true, new Model(componentId
				+ "_plode(document.getElementById('" + getTrigger().getId() + "'),'" + HTMLID
				+ "', " + getDuration() + ");")));
	}

	/**
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		Component c = getComponent();
		this.component = (Component)c;
		this.componentId = c.getId();

		// create a unique HTML for the explode component
		HTMLID = this.component.getId() + "_" + component.getPath();
		// Add ID to component, and bind effect to trigger
		this.component.add(new AttributeModifier("id", true, new Model(HTMLID)));
		if (!startDisplay)
		{
			this.component
					.add(new AppendAttributeModifier("style", true, new Model("display:none")));
		}

		// if from is set, set HTML id for from component, so it is
		// referenceable by javascript
		if (from != null)
		{
			from.add(new AttributeModifier("id", true, new Model(from.getId())));
		}
		this.getTrigger().add(
				new AppendAttributeModifier(getEventName(), true, new Model(componentId
						+ "_plode(this,'" + HTMLID + "', " + getDuration() + ");")));
		// set trigger html id to make it refenceable for possible extra
		// triggers
		this.getTrigger().add(
				new AppendAttributeModifier("id", true, new Model(getTrigger().getId())));

	}

	/**
	 * Inner Box class.
	 */
	private class Box
	{
		private final int x;
		private final int y;
		private final int width;
		private final int height;

		/**
		 * Construct.
		 * 
		 * @param x
		 *            the x position of the box
		 * @param y
		 *            the y position of the box
		 * @param width
		 *            the width of the box
		 * @param height
		 *            the height of the box
		 */
		public Box(int x, int y, int width, int height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		/**
		 * @return the x position of the box
		 */
		public int getX()
		{
			return x;
		}

		/**
		 * @return the y position of the box
		 */
		public int getY()
		{
			return y;
		}

		/**
		 * @return the width of the box
		 */
		public int getWidth()
		{
			return width;
		}

		/**
		 * @return the height of the box
		 */
		public int getHeight()
		{
			return height;
		}
	}


}
