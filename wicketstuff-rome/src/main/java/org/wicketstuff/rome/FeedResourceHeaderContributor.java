package org.wicketstuff.rome;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.value.ValueMap;

import com.sun.syndication.feed.synd.SyndFeed;

/**
 * create a {@link IHeaderContribution header contribution} that will render the link reference to the {@link FeedResource feed resource}.
 * allows for parameters to be passed to the feed.
 * 
 * @see http://blogs.msdn.com/rssteam/articles/PublishersGuide.aspx
 */
public class FeedResourceHeaderContributor extends HeaderContributor {

	public FeedResourceHeaderContributor(ResourceReference reference) {
		this(reference, null);
	}

	public FeedResourceHeaderContributor(final ResourceReference reference, final ValueMap params) {
		super(new IHeaderContributor() {
			private SyndFeed feed;

			public void renderHead(IHeaderResponse response) {
				CharSequence url = RequestCycle.get().urlFor(reference, params);

				FeedResource resource = (FeedResource) reference.getResource();
				this.feed = (resource == null ? null : resource.getFeed());
				
				StringBuffer buffer = new StringBuffer();
				buffer.append("<link rel=\"alternate\" ");
				buffer.append("type=\"").append(getFeedContentType()).append("\" ");
				buffer.append("title=\"").append(getFeedTitle()).append("\" ");
				buffer.append("href=\"").append(url).append("\" />");
				response.renderString(buffer.toString());
			}

			private String getFeedTitle() {
				if (feed == null) {
					return "Feed";
				}
				return feed.getTitle();
			}

			private String getFeedContentType() {
				if (feed == null) {
					return "text/xml";
				}
				return FeedResource.getFeedContentType(feed);
			}
		});
	}
}

