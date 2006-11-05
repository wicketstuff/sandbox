package wicket.contrib.dojo.examples.rssreader.widgets;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.basic.MultiLineLabel;
import wicket.markup.html.panel.Panel;

import com.sun.syndication.feed.synd.SyndEntryImpl;

public class NewsPanel extends Panel{
	
	public NewsPanel(MarkupContainer parent, String id, SyndEntryImpl entry) {
		super(parent,id);

		new Label(this, "date",entry.getPublishedDate().toString());
		new Label(this, "title",entry.getTitle());
		new MultiLineLabel(this, "desc", entry.getDescription().getValue());
	}	

}
