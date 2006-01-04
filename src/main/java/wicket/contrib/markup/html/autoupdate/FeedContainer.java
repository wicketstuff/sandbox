package wicket.contrib.markup.html.autoupdate;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import wicket.contrib.dojo.autoupdate.IUpdatable;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.basic.MultiLineLabel;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * WebmarkupContainer which displays information retrieved from an RSS feed.<br/>
 * Implements IUpdatable in order to be compatible with DojoAutoUpdateHandler<br/>
 * This class uses <a href="https://rome.dev.java.net/">ROME RSS library</a> to <br/>
 * parse the feed into Pojo.<br/>
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 */
public class FeedContainer extends WebMarkupContainer implements IUpdatable
{

	final String url;
	int maxEntries = 8;
	private ListView syndEntryListView;
	private List viewEntries = new ArrayList(); 
	
	/**
	 * Constructor.
	 * @param id Wicket Id
	 * @param url url to rss feed.
	 */
	public FeedContainer(String id, String url)
	{
		super(id);
		this.url = url;
		
		//syndEntryListView = new ListView("entryContainer", (IModel)listModel);
		update();

	}

	/**
	 * Update method for this RSS container. Retrieves new RSS data, <br/>
	 * truncate and order the list, and replace the old Model with the new one <br/>
	 * containing the new RSS data.<br/>
	 * @see wicket.contrib.markup.html.autoupdate.IUpdatable#update()
	 */
	public void update()
	{

				List entries = new ArrayList(); 
				viewEntries.clear();
				String test= "1";
				try {
						test = test + "2";
						/**Retrieve feed and sort list*/
					 
					 	URL feedUrl = new URL(url);

			            SyndFeedInput input = new SyndFeedInput();
			            SyndFeed feed = input.build(new XmlReader(feedUrl));

			            entries = feed.getEntries();

			            
			            Comparator c = new EntryComparator();
			            Collections.sort(entries, c);
			            
			            /**Truncate List to numItems, Create ListView, Create entries */         
			            //int numItems = (entries.size() <= maxEntries ? (entries.size()-1) : maxEntries);
			            int numItems = (entries.size() <= maxEntries ? entries.size() : maxEntries);
			    		
			            //entries = entries.subList(0, numItems);
			            
			    		for(int i = 0; i < numItems; i++)
			            {
			    			test = test + "3";
			    			SyndEntryImpl entry = (SyndEntryImpl)entries.get(i);
			    			viewEntries.add(new SimpleRssEntry(entry));
			            }
			    		//System.out.println("numentries: " + numItems);
			    		//System.out.println(viewEntries);

			        }
			        catch (Exception ex) {
			        	test = test + "4";
			        	ex.printStackTrace();
			            System.out.println("ERROR: "+ex.getMessage());
			        }

		            //syndEntryListView = new ListView("entries", viewEntries)
			        CompoundPropertyModel listModel = new CompoundPropertyModel(viewEntries);
			        
					if(syndEntryListView == null)
					{
						syndEntryListView = new ListView("entries", (IModel)listModel){
						  public void populateItem(final ListItem listItem)
						  {
							//System.out.println("populated item");
							final SimpleRssEntry entry = (SimpleRssEntry)listItem.getModelObject();
							listItem.add(new Label("date", new Model(entry.getPublishedDate())));
							listItem.add(new MultiLineLabel("title", entry.getTitle()));
						  }
					  };
						syndEntryListView.setVersioned(false);
						add(syndEntryListView);
						//System.out.println("innitial run: " + syndEntryListView.getModelObjectAsString());
					} else
					{
						syndEntryListView.setModel(listModel);
						syndEntryListView.modelChanged();
						//System.out.println("model updated: " + syndEntryListView.getModelObjectAsString());
					}

					test = test + "5";
					//System.out.println(test);
					
		 
		}
		
	
	/**
	 * Comparator class used to compare SindEntryImpl opject on their publishing dates.
	 * @author Marco van de Haar
	 * @author Ruud Booltink
	 *
	 */
	private class EntryComparator implements Comparator
	{
	
		/**
		 * compares 2 SyndEntryImpl objects on their publishing dates.
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object o1, Object o2)
		{
			Date date1 = ((SyndEntryImpl)o1).getPublishedDate();
			Date date2 = ((SyndEntryImpl)o2).getPublishedDate();
			//compare entries on publish date
			
			if(date1.compareTo(date2)<0)
			{
				return 1;
			} else if(date1.compareTo(date2)>0) {
				return -1;
			} 
			else //date1 == date2 
			{
				return 0;
			}
	
		}
		
	}

/**
 * serializable entry class to use in ListView
 * @author Marco va de Haar
 */
	private class SimpleRssEntry implements Serializable
	{
		private String title;
		private String Description;
		private Date publishedDate;
		
		/**
		 * constructor
		 * @param entry RSS entry object
		 */
		public SimpleRssEntry(SyndEntryImpl entry)
		{
			this.title = entry.getTitle();
			this.Description = entry.getDescription().getValue();
			this.publishedDate = entry.getPublishedDate();
		}
		
		public String getTitle()
		{
			return this.title;
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
	}

}
