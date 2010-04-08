package org.wicketstuff.rome;

import java.net.URL;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * parse an rss/atom feed and return a list of <code>SyndEntry</code> objects for display.
 * 
 * @author rsonnek
 * @see http://www.xml.com/pub/a/2006/02/22/rome-parse-publish-rss-atom-feeds-java.html?page=3
 */
public class SyndEntryListModel extends LoadableDetachableModel<List<?extends SyndEntry>> {
	private static final long serialVersionUID = 1L;
	private final String url;

	public SyndEntryListModel(String url) {
		this.url = url;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<?extends SyndEntry> load() {
		SyndFeedInput input = new SyndFeedInput();
		try {
			SyndFeed feed = input.build(new XmlReader(new URL(url)));
			return feed.getEntries();
		} catch (Exception e) {
			throw new RuntimeException("Unable to parse feed: " + url, e);
		} 
	}
}
