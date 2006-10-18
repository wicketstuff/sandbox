package wicket.contrib.dojo.examples.rssreader;

import java.util.Date;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.panel.Panel;

import com.sun.syndication.feed.synd.SyndEntryImpl;

public class RSSItemPanel extends Panel{

	private String title;
	private String Description;
	private String link;
	private Date publishedDate;
	private DescriptionPanel dPanel;
	
	public RSSItemPanel(MarkupContainer parent, String id, SyndEntryImpl entry, DescriptionPanel dpanel) {
		super(parent,id);

		this.title = entry.getTitle();
		this.Description = entry.getDescription().getValue();
		this.publishedDate = entry.getPublishedDate();
		this.link = entry.getLink();
		this.dPanel = dpanel;
		new Label(this, "date",getPublishedDate().toString());
		new Label(this, "title",getTitle());
		add(new DescriptionUpdateHandler());
	}

	
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getLink()
	{
		return link;
	}
	public String getDescription()
	{
		return this.Description;
	}
	
	public Date getPublishedDate()
	{
		return this.publishedDate;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Entry: " + getTitle() + " on date: " + getPublishedDate().toString();
	}
	
	/**
	 * @param extended Extended indicator.
	 * @return Extended toString for more info.
	 */
	public String toString(boolean extended)
	{
		return "Entry: " + getTitle() + " on date: " + getPublishedDate().toString() + " with description: " + getDescription();
	}
	
	public DescriptionPanel getDPanel()
	{
		return dPanel;
	}
	

	

}
