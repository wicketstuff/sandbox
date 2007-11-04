
package org.wicketstuff.examples.rome.feeds;

import org.apache.wicket.Resource;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.wicketstuff.rome.FeedResource;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * 
 * 
 * @author Sean C. Sullivan
 *
 */
public class FeedUtil
{
	static String getDefaultFeedType()
	{
		return "rss_2.0";
	}
	
	static public ResourceReference createResourceReference(final Class feedClass)
	{
		return new ResourceReference(getResourceName(feedClass))
		{
			protected Resource newResource()
			{
				return createFeedResource(feedClass);
			}
			
		};
	}
	
	static public HeaderContributor createAutodiscoveryLink(Class feedClass)
	{
		HeaderContributor contributor = FeedResource.autodiscoveryLink(createResourceReference(feedClass));
		return contributor;
	}
	
	static public FeedResource createFeedResource(final Class feedClass)
	{
		return new FeedResource()
		{

			protected SyndFeed getFeed()
			{
				return createFeed(feedClass);
			}
			
		};
	}

	static public String getResourceName(Class feedClass)
	{
		return feedClass.getName();
	}

	
	private static SyndFeed createFeed(Class feedClass)
	{
		try
		{
			Object obj = feedClass.newInstance();
			return (SyndFeed) obj;
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
		
	}
}
