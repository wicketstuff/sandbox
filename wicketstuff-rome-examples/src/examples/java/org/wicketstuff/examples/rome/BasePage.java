
package org.wicketstuff.examples.rome;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.examples.rome.feeds.*;

abstract public class BasePage extends WebPage
{
	public BasePage()
	{
		super();
	}
	
	protected void addFeed(Class feedClass)
	{
		add(FeedManager.createAutodiscoveryLink(feedClass));
	}
}
