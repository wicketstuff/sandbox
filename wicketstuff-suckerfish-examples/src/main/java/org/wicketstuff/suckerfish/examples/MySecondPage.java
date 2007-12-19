package org.wicketstuff.suckerfish.examples;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

/**
 * MySecondPage
 * 
 * Author: Julian Sinai http://javathoughts.capesugarbird.com
 * 
 * This sample code is released under the Apache 2 license.
 * 
 */
public class MySecondPage extends BasePage
{
	private static final long serialVersionUID = 1L;

	public MySecondPage(final PageParameters parameters)
	{
		super(parameters);

		// Add the simplest type of label
		add(new Label("message", "This is MySecondPage"));
	}
}
