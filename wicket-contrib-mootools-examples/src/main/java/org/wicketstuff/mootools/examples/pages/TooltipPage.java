package org.wicketstuff.mootools.examples.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.mootools.examples.WicketExamplePage;

import wicket.contrib.mootools.plugins.MFXTips;

public class TooltipPage extends WicketExamplePage
{
	public TooltipPage()
	{
		// Plugin automatically inserts the required CSS code needed
		Label lbl = new Label("label", "Label with tooltip");
		lbl.add(new MFXTips("Hello world!."));
		add(lbl);
	}
}
