package org.wicketstuff.rome;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.util.resource.AbstractStringResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.time.Time;
import org.apache.wicket.util.value.ValueMap;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * Wicket WebResource for streaming RSS/Atom Feeds.
 * A <code>WebPage</code> can add an {@link #autodiscoveryLink(ResourceReference) autodiscovery link} 
 * so that browsers can display icons for available RSS/Atom feeds.
 * 
 * @see https://rome.dev.java.net/
 * @see http://en.wikipedia.org/wiki/Web_syndication
 * 
 */
public abstract class FeedResource extends WebResource {

    /**
     * extension point for rendering the desired feed.
     */
	protected abstract SyndFeed getFeed();

	@Override
	public final IResourceStream getResourceStream() {
		return new FeedResourceStream(getFeed());
	}
	
	/**
	 * decode the correct content type depending on the {@link SyndFeed#getFeedType() feed type}
	 */
	private static String getFeedContentType(SyndFeed feed) {
		String type = feed.getFeedType();
		if (type.startsWith("atom")) {
			return "application/atom+xml";
		} else if (type.startsWith("rss_0")) {
			return "text/xml";
		}
		return "application/rss+xml";
	}

	/**
	 * @see #autodiscoveryLink(ResourceReference, ValueMap)
	 */
	public static HeaderContributor autodiscoveryLink(ResourceReference reference) {
		return autodiscoveryLink(reference, null);
	}

	/**
	 * create a {@link IHeaderContribution header contribution} that will render an autodiscovery link to the {@link FeedResource feed resource}.
	 * allows for parameters to be passed to the feed.
	 * The link content type is based upon the {@link #getFeedContentType(SyndFeed) feed's content type}.
	 * The link title is is based upon the feed's title.
	 * 
	 * @see http://blogs.msdn.com/rssteam/articles/PublishersGuide.aspx
	 */
	public static HeaderContributor autodiscoveryLink(final ResourceReference reference, final ValueMap params) {
		return new HeaderContributor(new IHeaderContributor() {
			public void renderHead(IHeaderResponse response) {
				CharSequence url = RequestCycle.get().urlFor(reference, params);

				FeedResource resource = (FeedResource) reference.getResource();
				SyndFeed feed = (resource == null ? null : resource.getFeed());

				String contentType = (feed == null ? "text/xml" : getFeedContentType(feed));
				String feedTitle = (feed == null ? "Feed" : feed.getTitle());

				StringBuffer buffer = new StringBuffer();
				buffer.append("<link rel=\"alternate\" ");
				buffer.append("type=\"").append(contentType).append("\" ");
				buffer.append("title=\"").append(feedTitle).append("\" ");
				buffer.append("href=\"").append(url).append("\" />");
				response.renderString(buffer.toString());
			}
		});
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
