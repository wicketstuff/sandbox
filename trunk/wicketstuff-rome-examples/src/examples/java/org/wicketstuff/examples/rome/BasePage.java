
package org.wicketstuff.examples.rome;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.examples.rome.feeds.*;

abstract public class BasePage extends WebPage
{
	public BasePage()
	{
		super();
	}
	
	protected void addFeedAutoDiscovery(Class feedClass)
	{
		add(FeedManager.createAutoDiscovery(feedClass));
	}
	
	protected void addFeedLink(String id, Class feedClass)
	{
		add(FeedManager.createLink(id, feedClass));
	}
}
