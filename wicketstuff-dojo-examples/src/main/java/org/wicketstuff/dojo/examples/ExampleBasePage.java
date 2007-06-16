package org.wicketstuff.dojo.examples;

import org.apache.wicket.markup.html.WebPage;

/**
 * Base class for all pages in the QuickStart application. Any page which
 * subclasses this page can get session properties from ExampleSession via
 * getQuickStartSession().
 */
public abstract class ExampleBasePage extends WebPage
{
	/**
	 * Get downcast session object for easy access by subclasses
	 * 
	 * @return The session
	 */
	public ExampleSession getQuickStartSession()
	{
		return (ExampleSession)getSession();
	}
}
