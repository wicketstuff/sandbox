package org.wicketstuff.suckerfish.examples;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

/**
 * MyFirstPage
 * 
 * Author: Julian Sinai http://javathoughts.capesugarbird.com
 * 
 * This sample code is released under the Apache 2 license.
 * 
 */
public class MyFirstPage extends BasePage
{
	private static final long serialVersionUID = 1L;

	public MyFirstPage(final PageParameters parameters)
	{
		super(parameters);

		// Add the simplest type of label
		add(new Label("message", "This is MyFirstPage"));
	}
}
