
package org.wicketstuff.examples.rome;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.examples.rome.feeds.*;

public class HomePage extends BasePage
{
	public HomePage()
	{
		super();
		
		addFeed(NewsFeed.class);
		
		addFeed(BlogFeed.class);
		
	}
	
}
