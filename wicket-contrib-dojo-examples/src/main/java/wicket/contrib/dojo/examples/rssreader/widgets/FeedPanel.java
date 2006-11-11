package wicket.contrib.dojo.examples.rssreader.widgets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Date;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractDojoTimerBehavior;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.basic.MultiLineLabel;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.util.time.Duration;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * Show a feed description
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class FeedPanel extends Panel{
	
	private List<SyndEntryImpl> newsElements;
	private String url;
	
	public FeedPanel(MarkupContainer parent, String id, IModel<String> model) {
		super(parent, id, model);
		url = (String)getModelObject();
		populateFeed();
		
		WebMarkupContainer newsContainer = new WebMarkupContainer(this, "newsContainer");
		
		newsElements = getNewsList(url);
		
		ListView newsList = new ListView(newsContainer, "news", newsElements) {
		
			@Override
			protected void populateItem(ListItem item) {
				SyndEntryImpl current = (SyndEntryImpl)item.getModelObject();
				new NewsPanel(item, "content", current);
			}
		
		};
		
		newsContainer.add(new AbstractDojoTimerBehavior(Duration.milliseconds(120000),"") {
			protected void onTimer(AjaxRequestTarget target) {
				ListView list = (ListView)((WebMarkupContainer)getComponent()).get("news");
				list.setList(getNewsList(url));
			}
		});
		
	}

	
	/**
	 * Populate the rss FeedPanel with the feed url
	 * @param url
	 * @return the feed matching the url
	 */
	private static SyndFeed getFeed(String url){
		try {
			URL feedUrl;
			feedUrl = new URL(url);
			SyndFeedInput input = new SyndFeedInput();
	        SyndFeed synd = input.build(new XmlReader(feedUrl));
	        return synd;
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static List<SyndEntryImpl> getNewsList(String url){
		SyndFeed feed = getFeed(url);
		if (feed != null){
			return (List<SyndEntryImpl>) feed.getEntries();
		}
		return null;
	}

	/**
	 * Create the header for a feed
	 *
	 */
	public void populateFeed()
	{
		SyndFeed feed = getFeed(url);
		String description = feed.getDescription();		
		MultiLineLabel desc = new MultiLineLabel(this, "desc", new Model(description));
		desc.setEscapeModelStrings(false);
		
		new Label(this, "title",feed.getTitle());
		Date publishedDate = feed.getPublishedDate();
		if (publishedDate != null){
			new Label(this, "date",feed.getPublishedDate().toString());
		}
		else {
			new Label(this, "date","?????");
		}
		
		modelChanged();
	}

}
