package org.wicketstuff.mootools.examples.pages;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.mootools.examples.WicketExamplePage;

import wicket.contrib.mootools.plugins.MFXFontResizer;

public class ResizePage extends WicketExamplePage
{
	public ResizePage()
	{
		// webmarkupcontainer is a href link with an image in it.
		// increase the font size by +2 for each click, do it only in the dev
		// with id #stage
		add(new WebMarkupContainer("larger").add(new MFXFontResizer(2).setContainer("stage")));

		// decrease the font size by -2 for each click on the whole page
		add(new WebMarkupContainer("smaller").add(new MFXFontResizer(-2)));
	}
}
