package org.wicketstuff.suckerfish.examples;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Homepage
 * 
 * Author: Julian Sinai http://javathoughts.capesugarbird.com
 * 
 * This sample code is released under the Apache 2 license.
 * 
 */
public class HomePage extends BasePage
{
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters)
	{
		super(parameters);

		// Add the simplest type of label
		add(new Label("message",
				"If you see this message wicket is properly configured and running"));
	}
}
