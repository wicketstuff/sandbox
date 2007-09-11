package org.wicketstuff.rome;

import java.util.Locale;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.Resource;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.value.ValueMap;

import com.sun.syndication.feed.synd.SyndFeed;

public class FeedResourceReference extends ResourceReference {

	public FeedResourceReference(Class scope, String name) {
		super(scope, name);
	}

	public FeedResourceReference(Class scope, String name, Locale locale, String style) {
		super(scope, name, locale, style);
	}

	public FeedResourceReference(String name) {
		super(name);
	}

	/**
	 * create a header contribution that will render the link reference to the feed resource.
	 * 
	 * @see http://blogs.msdn.com/rssteam/articles/PublishersGuide.aspx
	 */
	public HeaderContributor headerContribution() {
		return headerContribution(null);
	}

	/**
	 * create a header contribution that will render the link reference to the feed resource.
	 * allows for parameters to be passed to the feed.
	 * 
	 * @see http://blogs.msdn.com/rssteam/articles/PublishersGuide.aspx
	 */
	public HeaderContributor headerContribution(final ValueMap parameters) {
		return new HeaderContributor(new IHeaderContributor() {
			public void renderHead(IHeaderResponse response) {
				CharSequence url = RequestCycle.get().urlFor(FeedResourceReference.this, parameters);

				StringBuffer buffer = new StringBuffer();
				buffer.append("<link rel=\"alternate\" ");
				buffer.append("type=\"").append(getFeedContentType()).append("\" ");
				buffer.append("title=\"").append(getFeedTitle()).append("\" ");
				buffer.append("href=\"").append(url).append("\" />");
				response.renderString(buffer.toString());
			}
		});
	}

	@Override
	protected Resource newResource() {
		return newFeedResource();
	}

	protected FeedResource newFeedResource() {
		return null;
	}
	
	private SyndFeed getFeed() {
		return getFeedResource().getFeed();
	}

	private FeedResource getFeedResource() {
		return (FeedResource)getResource();
	}

	/**
	 * safe implementation is to have link be text/xml
	 * @return
	 */
	protected String getFeedContentType() {
		return getFeedResource().getFeedContentType();
	}
	
	protected String getFeedTitle() {
		return getFeed().getTitle();
	}
}

