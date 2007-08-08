package org.wicketstuff.mootools.examples.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.mootools.examples.WicketExamplePage;

import wicket.contrib.mootools.effects.MFXStyle;
import wicket.contrib.mootools.events.MFXWindowLoad;

public class EffectsPage extends WicketExamplePage
{
	public EffectsPage()
	{
		// window onload event
		MFXWindowLoad load = new MFXWindowLoad();

		// append the style to the window onload event
		load.addStyle(new MFXStyle("margin-left", -400, 0));

		// append event to the element that will have the animation
		Label lbl = new Label("label", "hello world");
		lbl.add(load);
		add(lbl);
	}
}
