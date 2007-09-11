package org.wicketstuff.rome;

import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.util.resource.AbstractStringResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.time.Time;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public abstract class FeedResource extends WebResource {

	@Override
	public final IResourceStream getResourceStream() {
		return new FeedResourceStream();
	}

	protected abstract SyndFeed getFeed();

	/**
	 * decode the correct content type depending on the {@link SyndFeed#getFeedType() feed type}
	 */
	public String getFeedContentType() {
		String type = getFeed().getFeedType();
		if (type.startsWith("atom")) {
			return "application/atom+xml";
		} else if (type.startsWith("rss_0")) {
			return "text/xml";
		}
		return "application/rss+xml";
	}

	/**
	 * resource stream for handling setting content type of content.
	 */
	private class FeedResourceStream extends AbstractStringResourceStream {
		private final SyndFeedOutput output = new SyndFeedOutput();

		@Override
		public String getContentType() {
			return getFeedContentType();
		}

		@Override
		protected String getString() {
			try {
				return output.outputString(getFeed());
			} catch (FeedException e) {
				throw new RuntimeException("Error streaming feed.", e);
			}
		}

		@Override
		public Time lastModifiedTime() {
			return Time.valueOf(getFeed().getPublishedDate());
		}
	}
}


