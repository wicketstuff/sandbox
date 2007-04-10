/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.dojo.dojofx;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.Model;

/**
 * TODO HEY STUDENTS! didn't they tought you documentation is important? If
 * Martijn finds out, it'll cost you points.
 */
public class FXOnClickHighlighter extends DojoFXHandler
{

	private final String type;
	private String HTMLID;
	private String componentId;
	private RGB startColor;
	private RGB endColor;

	/**
	 * @param duration
	 * @param trigger
	 * @param toR
	 * @param toG
	 * @param toB
	 */
	public FXOnClickHighlighter(int duration, Component trigger, int toR, int toG, int toB)
	{
		super("onclick", duration, trigger);
		this.type = "b2c";
		endColor = new RGB(toR, toG, toB);

	}

	/**
	 * @param duration
	 * @param trigger
	 * @param startR
	 * @param startG
	 * @param startB
	 * @param endR
	 * @param endG
	 * @param endB
	 */
	public FXOnClickHighlighter(int duration, Component trigger, int startR, int startG,
			int startB, int endR, int endG, int endB)
	{
		super("OnClick", duration, trigger);
		this.type = "c2c";
		startColor = new RGB(startR, startG, startB);
		endColor = new RGB(endR, endG, endB);
	}

	private class RGB
	{
		private final int R;
		private final int G;
		private final int B;

		/**
		 * @param R
		 * @param G
		 * @param B
		 */
		public RGB(int R, int G, int B)
		{
			this.R = R;
			this.G = G;
			this.B = B;

		}

		/**
		 * @return Red int value
		 */
		public int getR()
		{
			return R;
		}

		/**
		 * @return Green int value
		 */
		public int getG()
		{
			return G;
		}

		/**
		 * @return Blue int value
		 */
		public int getB()
		{
			return B;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			return "[" + R + ", " + G + ", " + B + "]";
		}

	}


	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
//		 String to be written to the header
		String s;
		// dojo function calls for highlight/unhighlight
		String highlightInFunction;
		String highlightOutFunction;

		// set the correct dojo functions for the type of highlighter
		if (type == "c2c")
		{
			highlightInFunction = "dojo.lfx.html.colorFade(node, " + startColor.toString() + ","
					+ endColor.toString() + ", duration, function(){" + componentId
					+ "_highlighterState='highlighted';});";
			highlightOutFunction = "dojo.lfx.html.colorFade(node, " + endColor.toString() + ","
					+ startColor.toString() + ", duration, function(){" + componentId
					+ "_highlighterState='unhighlighted';});";
		}
		else
		{
			highlightInFunction = "dojo.lfx.html.colorFadeOut(node, " + endColor.toString()
					+ ", duration ,0,function(){" + componentId
					+ "_highlighterState='highlighted';});";
			highlightOutFunction = "dojo.lfx.html.colorFadeOut(node, startbc, duration ,0,function(){"
					+ componentId + "_highlighterState='unhighlighted';});";
		}

		s = "\t<script language=\"JavaScript\" type=\"text/javascript\">\n" + "\t" + componentId
				+ "_highlighterState = 'unhighlighted'; \n" + "\t" + componentId
				+ "_first = false; \n" + "\tfunction " + componentId
				+ "_highlight(id, duration) { \n" + "\t\tif(" + componentId
				+ "_highlighterState!='highlighting'){\n"
				+ "\t\t\tnode = document.getElementById(id);\n" +

				"\t\t\tif(!" + componentId + "_first){\n" + "\t\t\t" + componentId
				+ "_first = true; \n" + "\t\t\t\tstartbc = dojo.html.getBackgroundColor(node);\n"
				+ "\t\t\t}\n" + "\t\t\tif(" + componentId
				+ "_highlighterState == 'unhighlighted') \n" + "\t\t\t{ \n" + "\t\t\t\t"
				+ componentId + "_highlighterState = 'highlighting';\n" + "\t\t\t\t"
				+ highlightInFunction + "\n" + "\t\t\t} else {\n" + "\t\t\t\t" + componentId
				+ "_highlighterState = 'highlighting';\n" + "\t\t\t\t" + highlightOutFunction
				+ "\n" + "\t\t\t}\n" + "\t\t}\n" + "\t}\n" + "\t</script>\n";

		response.renderString(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		Component c = getComponent();
		this.component = (Component)c;
		this.componentId = c.getId();

		// create a unique HTML for the wipe component
		HTMLID = this.component.getId() + "_" + component.getPath();
		// Add ID to component, and bind effect to trigger

		this.component.add(new AttributeModifier("id", true, new Model(HTMLID)));
		this.getTrigger().add(
				new AppendAttributeModifier(getEventName(), true, new Model(componentId
						+ "_highlight('" + HTMLID + "', " + getDuration() + ");")));

	}


}
