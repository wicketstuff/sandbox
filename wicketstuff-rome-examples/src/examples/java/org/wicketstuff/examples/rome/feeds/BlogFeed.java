
package org.wicketstuff.examples.rome.feeds;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import java.util.*;

/*
 * 
 * @author Sean C. Sullivan
 * 
 */
public class BlogFeed extends SyndFeedImpl
{
	public BlogFeed()
	{
		super();
		
		this.setAuthor("Joe Blogger");
		this.setPublishedDate(new java.util.Date());
		this.setTitle("Joe's blog (" + this.getClass().getName() + ")");
		this.setFeedType(FeedUtil.getDefaultFeedType());
		this.setDescription("Joe's blog");
		
		List entries = new ArrayList();
		
		for (int i = 0; i < 5; i++)
		{
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle("Entry-" + i);
			entry.setLink("http://wicket.apache.org/");
		}
		
		this.setEntries(entries);
		
	}
}
