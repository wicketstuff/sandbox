
package org.wicketstuff.examples.rome;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.examples.rome.feeds.*;

public class HomePage extends BasePage
{
	public HomePage()
	{
		super();
		
		addFeedAutoDiscovery(NewsFeed.class);
		
		addFeedLink("newsFeedLink", NewsFeed.class);
		
		addFeedAutoDiscovery(BlogFeed.class);
		
		addFeedLink("blogFeedLink", BlogFeed.class);
	}
	
}
