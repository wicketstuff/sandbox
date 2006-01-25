package wicket.contrib.dojo.examples.rssreader;

import java.io.Serializable;
import java.util.Date;

import wicket.markup.html.basic.MultiLineLabel;

public class DescriptionModel implements Serializable {

	private String title  = "";
	private Date date = new Date();;
	private String link = "";
	private String description;

	
	public DescriptionModel(String description)
	{
		this.description = description;
		
	}
	
	public DescriptionModel(String title, Date date, String link, String description)
	{
		this.title = title;
		this.date = date;
		this.link = link;
		this.description = description;
		
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getLink()
	{
		return this.link;
	}
	
	public Date getDate()
	{
		return this.date;
	}
	
	public String getDateString()
	{
		//customize return string for your own date format
		return this.date.toString();
	}

	public String toString()
	{
		return this.description;	
	}

}
