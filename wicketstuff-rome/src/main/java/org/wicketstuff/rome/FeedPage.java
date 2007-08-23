package org.wicketstuff.rome;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * Stream any feed content using a wicket page.
 * 
 * @see https://rome.dev.java.net/
 * 
 * @author rsonnek
 */
public abstract class FeedPage extends WebPage {
    public FeedPage() {
        add(new FeedOutputComponent("feed"));
    }

    /**
     * get the feed to display.
     * this method is "lazy" to allow for users to take page parameters 
     * and lookup information before the feed is created.
     * @return
     */
    protected abstract SyndFeed getFeed();
    
    @Override
	public final String getMarkupType() {
    	return "xml";
	}
    
    /**
     * component to output the feed.
     * the output *will* include an xml encoding declaration at the top.
     */
    private class FeedOutputComponent extends WebMarkupContainer {
    	public FeedOutputComponent(String id) {
    		super(id);
    		
    		setRenderBodyOnly(true);
    	}

        @Override
		protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
			super.onComponentTagBody(markupStream, openTag);
			
            try {
                SyndFeed feed = getFeed();

                String value = new SyndFeedOutput().outputString(feed);
                
                getResponse().write(value);
            } catch (FeedException e) {
                throw new RuntimeException("Error streaming feed.", e);
            }
		}
    }
}
