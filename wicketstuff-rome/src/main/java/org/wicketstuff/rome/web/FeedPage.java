package org.wicketstuff.rome.web;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebPage;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * Nice simple glue page from http://jroller.com/wireframe/entry/wicket_feedpage
 */
public abstract class FeedPage extends WebPage
{
	@Override
	public String getMarkupType()
	{
		return "xml";
	}

	@Override
	protected final void onRender(MarkupStream markupStream)
	{
		PrintWriter writer = new PrintWriter(getResponse().getOutputStream());
		SyndFeedOutput output = new SyndFeedOutput();
		try
		{
			output.output(getFeed(), writer);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Error streaming feed.", e);
		}
		catch (FeedException e)
		{
			throw new RuntimeException("Error streaming feed.", e);
		}
	}

	protected abstract SyndFeed getFeed();
}
