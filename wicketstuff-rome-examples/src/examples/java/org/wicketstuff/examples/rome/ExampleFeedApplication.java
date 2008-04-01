
package org.wicketstuff.examples.rome;

import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.examples.rome.feeds.BlogFeed;
import org.wicketstuff.examples.rome.feeds.FeedManager;
import org.wicketstuff.examples.rome.feeds.NewsFeed;

/**
 * 
 * 
 * @author Sean C. Sullivan
 *
 *
 */
public class ExampleFeedApplication extends WebApplication
{
	protected void init()
	{
		super.init();
		
		mountFeeds();
	}

	protected void mountFeeds()
	{
		final String FEED_ROOT = "feeds";
		
		FeedManager.mountFeed(this, NewsFeed.class, FEED_ROOT + "/news");
		FeedManager.mountFeed(this, BlogFeed.class, FEED_ROOT + "/blog");
	}
	
	public Class getHomePage()
	{
		return HomePage.class;
	}

}
