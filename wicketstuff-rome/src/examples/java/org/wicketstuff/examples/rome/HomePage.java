
package org.wicketstuff.examples.rome;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.examples.rome.feeds.*;

public class HomePage extends WebPage
{
	public HomePage()
	{
		super();
		
		add(FeedUtil.createAutodiscoveryLink(NewsFeed.class));
		
		add(FeedUtil.createAutodiscoveryLink(BlogFeed.class));
	}
}
