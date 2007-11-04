
package org.wicketstuff.examples.rome;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.SharedResources;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.examples.rome.feeds.BlogFeed;
import org.wicketstuff.examples.rome.feeds.FeedUtil;
import org.wicketstuff.examples.rome.feeds.NewsFeed;
import org.wicketstuff.rome.FeedResource;

/**
 * 
 * 
 * @author Sean C. Sullivan
 *
 *
 */
public class ExampleFeedApplication extends WebApplication
{
	public static final String FEED_ROOT = "feeds";
	
	protected void init()
	{
		super.init();
		
		mountFeeds();
		
	}

	protected void mountFeeds()
	{
		mountFeed(NewsFeed.class, FEED_ROOT + "/news");
		mountFeed(BlogFeed.class, FEED_ROOT + "/blog");
	}
	
	protected void mountFeed(Class feedClass, String resourcePath)
	{
		SharedResources resources = this.getSharedResources();
		
		String resourceName = FeedUtil.getResourceName(feedClass);
		FeedResource resource = FeedUtil.createFeedResource(feedClass);
		
		resources.add(resourceName, resource);
		
		// resources.putClassAlias(feedClass, alias);
		
		ResourceReference ref = FeedUtil.createResourceReference(feedClass);
		
		mountSharedResource(resourcePath, ref.getSharedResourceKey());
		
	}
	
	public Class getHomePage()
	{
		return HomePage.class;
	}

}
