
package org.wicketstuff.examples.rome;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.wicketstuff.examples.rome.feeds.BlogFeed;
import org.wicketstuff.examples.rome.feeds.NewsFeed;
import org.wicketstuff.rome.SyndEntryListModel;

import com.sun.syndication.feed.synd.SyndEntry;

public class HomePage extends BasePage
{
	public HomePage()
	{
		super();
		
		addFeedAutoDiscovery(NewsFeed.class);
		
		addFeedLink("newsFeedLink", NewsFeed.class);
		
		addFeedAutoDiscovery(BlogFeed.class);
		
		addFeedLink("blogFeedLink", BlogFeed.class);
		
		add(new ListView("entry", new SyndEntryListModel("http://feeds.feedburner.com/code_poet")) {
			@Override
			protected void populateItem(ListItem item) {
				SyndEntry entry = (SyndEntry) item.getModelObject();
				item.add(new Label("title", entry.getTitle()));
			}
		});
	}
	
}
