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

import java.util.StringTokenizer;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.Model;

/**
 * Class that can add fading functionality to a vissible Wicket Component This
 * class let's you set a trigger, a duration, a start opacity and an end
 * opacity.
 * 
 * @TODO usage examples
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 */
public class FXOnClickFader extends DojoFXHandler
{

	private final boolean startDisplay;
	private final String type;
	private String HTMLID;
	private String componentId;
	private double startOpac;
	private double endOpac;


	/**
	 * convenience contructor with 2 parameters; sets startDisplay default to
	 * false
	 * 
	 * @param duration
	 *            duration of the fade effect
	 * @param trigger
	 *            Component which ONCLICK method will be bound to trigger the
	 *            fade effect.
	 */
	public FXOnClickFader(int duration, Component trigger)
	{
		super("onclick", duration, trigger);
		this.startDisplay = false;
		this.type = "fade";
	}


	/**
	 * Constructor to make a simple fade effect with a trigger and a duration.
	 * 
	 * @param duration
	 *            duration of the fade effect
	 * @param trigger
	 *            Component which ONCLICK method will be bound to trigger the
	 *            fade effect.
	 * @param startDisplay
	 *            default false; true if component should start with full
	 *            opacity
	 */
	public FXOnClickFader(int duration, Component trigger, boolean startDisplay)
	{
		super("onclick", duration, trigger);
		this.startDisplay = startDisplay;
		this.type = "fade";
	}

	/**
	 * Constructor for fade effect. If allowHide is true, component's display
	 * style will be ajusted on a fade.
	 * 
	 * @param duration
	 *            duration of the fade effect
	 * @param trigger
	 *            Component which ONCLICK method will be bound to trigger the
	 *            fade effect.
	 * @param startDisplay
	 *            default false; true if component should start with full
	 *            opacity
	 * @param allowHide
	 *            if true component's display will be set to non when wiped-out,
	 *            hence component will be removed from rendered page.
	 */
	public FXOnClickFader(int duration, Component trigger, boolean startDisplay, boolean allowHide)
	{
		super("Onclick", duration, trigger);
		this.startDisplay = startDisplay;

		if (allowHide)
		{
			this.type = "fadeHide";
		}
		else
		{
			this.type = "fade";
		}
	}

	/**
	 * Constructor for a fade effect where the starting opacity and the ending
	 * opacity are set.
	 * 
	 * @param duration
	 *            duration of the fade effect
	 * @param trigger
	 *            Component which ONCLICK method will be bound to trigger the
	 *            fade effect.
	 * @param startDisplay
	 *            default false; true if component should start with starting
	 *            opacity (startOpac)
	 * @param startOpac
	 *            starting opacity between 1 and 0. (0 for transparant, 1 for
	 *            opaque)
	 * @param endOpac
	 *            ending opacity between 1 and 0. (0 for transparant, 1 for
	 *            opaque)
	 */
	public FXOnClickFader(int duration, Component trigger, boolean startDisplay, double startOpac,
			double endOpac)
	{
		super("Onclick", duration, trigger);
		this.startDisplay = startDisplay;

		this.type = "fadeOpac";

		if ((startOpac < 0.0) || (startOpac > 1.0))
		{
			System.out.println("WARNING: startOpac has to be between 0 and 1");
		}

		if ((endOpac < 0.0) || (endOpac > 1.0))
		{
			System.out.println("WARNING: endOpac has to be between 0 and 1");
		}

		this.startOpac = startOpac;
		this.endOpac = endOpac;
	}

	/*
	 * @see wicket.AjaxHandler#getBodyOnloadContribution()
	 */
	protected String getBodyOnloadContribution()
	{
		// set initial opacity to fadeOpac if type is fadeOpac, else to 0
		double initOpac = (type == "fadeOpac" ? startOpac : 0);
		// if starDisplay is false, set starting opacity of component to
		// initOpac.
		if (!startDisplay)
		{
			return "dojo.html.setOpacity(document.getElementById('" + HTMLID + "'), " + initOpac + ");";
		}
		else
		{
			return null;
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
		// dojo function calls for fadein/out
		String fadeInFunction;
		String fadeOutFunction;

		// set the correct dojo functions for the type of fader
		if (type == "fadeHide")
		{
			fadeInFunction  = "dojo.lfx.html.fadeShow(node.id, duration, null, function(){" + HTMLID + "_faderState='fadedIn';})";
			fadeOutFunction = "dojo.lfx.html.fadeHide(node.id, duration, null, function(){" + HTMLID + "_faderState='fadedOut';})";
		}
		else if (type == "fadeOpac")
		{
			fadeInFunction  = "dojo.lfx.html.fade(node.id, {start:" + startOpac + ",end:" + endOpac   + "}, duration, null, function(){" + HTMLID + "_faderState='fadedIn';}).play();";
			fadeOutFunction = "dojo.lfx.html.fade(node.id, {start:" + endOpac   + ",end:" + startOpac + "}, duration, null, function(){" + HTMLID + "_faderState='fadedOut';}).play();";
		}
		else
		{
			fadeInFunction  = " dojo.lfx.html.fadeIn(node.id, duration, null, function(){" + HTMLID + "_faderState='fadedIn';}).play()";
			fadeOutFunction = "dojo.lfx.html.fadeOut(node.id, duration, null, function(){" + HTMLID + "_faderState='fadedOut'}).play();";
		}

		// set the correct state for the startDisplay value
		if (startDisplay)
		{
			s = "\t<script language=\"JavaScript\" type=\"text/javascript\">\n" + "\t"
					+ HTMLID + "_faderState = 'fadedIn'; \n";
		}
		else
		{
			s = "\t<script language=\"JavaScript\" type=\"text/javascript\">\n" + "\t"
					+ HTMLID + "_faderState = 'fadedOut'; \n";
		}

		s = s + "\tfunction " + HTMLID + "_fade(id, duration) { \n" + "\t\tif(" + HTMLID
				+ "_faderState!='fading'){\n" + "\t\t\tnode = document.getElementById(id);\n"
				+ "\t\t\tif(" + HTMLID + "_faderState == 'fadedOut') \n" + "\t\t\t{ \n"
				+ "\t\t\t\t" + HTMLID + "_faderState = 'fading';\n" + "\t\t\t\t"
				+ fadeInFunction + "\n" + "\t\t\t} else {\n" + "\t\t\t\t" + HTMLID
				+ "_faderState = 'fading';\n" + "\t\t\t\t" + fadeOutFunction + "\n" + "\t\t\t}\n"
				+ "\t\t}\n" + "\t}\n" + "\t</script>\n";
		response.renderString(s);
	}

	private String removeColon(String s) {
		  StringTokenizer st = new StringTokenizer(s,":",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
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
		
		String componentpath = removeColon(component.getPath());
		// create a unique HTML for the wipe component
		this.HTMLID = "f_" + this.component.getId() + "_" + componentpath;
		// Add ID to component, and bind effect to trigger
		this.component.add(new AttributeModifier("id", true, new Model(HTMLID)));
		this.getTrigger().add(
				new AppendAttributeModifier(getEventName(), true, new Model(HTMLID + "_fade('"
						+ HTMLID + "', " + getDuration() + ");")));

	}



}
