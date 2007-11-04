
package org.wicketstuff.examples.rome;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.examples.rome.feeds.*;

public class HomePage extends WebPage
{
	public HomePage()
	{
		super();
		
		addFeed(NewsFeed.class);
		
		addFeed(BlogFeed.class);
		
	}
	
	protected void addFeed(Class feedClass)
	{
		add(FeedUtil.createAutodiscoveryLink(feedClass));
	}
}
