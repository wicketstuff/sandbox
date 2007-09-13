package org.wicketstuff.rome;

import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.util.resource.AbstractStringResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.time.Time;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * Wicket WebResource for streaming RSS/Atom Feeds.
 * 
 * @see https://rome.dev.java.net/
 */
public abstract class FeedResource extends WebResource {

    /**
     * get the feed to display.
     */
	protected abstract SyndFeed getFeed();

	@Override
	public final IResourceStream getResourceStream() {
		return new FeedResourceStream(getFeed());
	}

	/**
	 * decode the correct content type depending on the {@link SyndFeed#getFeedType() feed type}
	 */
	static String getFeedContentType(SyndFeed feed) {
		String type = feed.getFeedType();
		if (type.startsWith("atom")) {
			return "application/atom+xml";
		} else if (type.startsWith("rss_0")) {
			return "text/xml";
		}
		return "application/rss+xml";
	}

	/**
	 * resource stream for streaming feed content.
	 */
	private static class FeedResourceStream extends AbstractStringResourceStream {
		private final SyndFeedOutput output = new SyndFeedOutput();
		private final SyndFeed feed;
		
		public FeedResourceStream(SyndFeed feed) {
			this.feed = feed;
		}

		@Override
		public String getContentType() {
			return getFeedContentType(feed);
		}

		@Override
		protected String getString() {
			try {
				return output.outputString(feed);
			} catch (FeedException e) {
				throw new RuntimeException("Error streaming feed.", e);
			}
		}

		@Override
		public Time lastModifiedTime() {
			return Time.valueOf(feed.getPublishedDate());
		}
	}
}


