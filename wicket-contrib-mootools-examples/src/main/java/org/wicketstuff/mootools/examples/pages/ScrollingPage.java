package org.wicketstuff.mootools.examples.pages;

import org.wicketstuff.mootools.examples.WicketExamplePage;

import wicket.contrib.mootools.plugins.MFXSmoothScroll;

public class ScrollingPage extends WicketExamplePage
{
	public ScrollingPage()
	{
		// add MFXSmoothScroll to your base page or current page
		add(new MFXSmoothScroll());

		// now all anchor clicks will have smooth scrolling on your web page
	}
}
