package wicket.contrib.dojo.examples.rssreader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import wicket.AttributeModifier;
import wicket.WicketRuntimeException;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.model.Model;

public class MainContainer extends WebMarkupContainer
{

	private ArrayList feedUrlList;
	private final int maxFeeds = 4;
	private ListView feedListView;
	private final DescriptionPanel dp;
	private String HTMLID;
	
	public MainContainer(String id, DescriptionPanel d) 
	{
		super(id);
		String componentpath = removeColon(getPath());
		this.HTMLID = "p_" + getId() + "_" + componentpath;
		add(new AttributeModifier("id",true,new Model(this.HTMLID)));
		feedUrlList =  new ArrayList(maxFeeds);
		if(d != null)
		{
			this.dp = d;	
		}
		else
		{
			throw(new WicketRuntimeException("DescriptionPanel can not be null!"));
		}
		
		
		fillContainer();
		
	}
	
	/**
	 * updates the list of feed urls with the given URL.
	 * @param url url to the RSS feed
	 */
	public void addFeed(String url)
	{
		if(spaceFree())
		{
			feedUrlList.add(0,url);
		}
		else
		{
			//if maxfeeds are already showing,
			//move entire list 1 place to right,
			//and put url in front.
			for(int i = (maxFeeds-2); i>=0; --i)
			{
				feedUrlList.set(i+1, feedUrlList.get(i));
			}
			feedUrlList.set(0, url);
		}

		fillContainer();
		
	}
	
	/**
	 * add method which replaces a feed at index i with new feed url.
	 * @param i index where to put the feed
	 * @param url url to the feed
	 */
	public void addFeed(int i, String url)
	{

		if(i < maxFeeds && i >= 0)
		{
			feedUrlList.set(i, url);
			fillContainer();
		}
		else
		{
			throw(new WicketRuntimeException("Feed cannot be added to specified index."));
		}
	}
	
	/**
	 * @param i index of feed to remove.
	 */
	public void removeFeed(int i)
	{
		feedUrlList.remove(i);
		fillContainer();
	}
	
	/**
	 * Fills the container with the feedContainers when ListView is null 
	 * or changes the modelObject of the ListView.
	 * 
	 */
	public void fillContainer()
	{
		ArrayList feedList = new ArrayList(maxFeeds);
		if(!feedUrlList.isEmpty())
		{
			Iterator iter = feedUrlList.iterator();
			int i = 0;
			while(iter.hasNext())
			{		
				String feedUrl = ((String)iter.next());
				if(feedUrl != null)
				{
					FeedContainer c = new FeedContainer("entryContainer", i , feedUrl, dp);
					feedList.add(c);
				}
				CompoundPropertyModel listModel = new CompoundPropertyModel(feedList);
				
				if(feedListView == null)
				{
					feedListView = new ListView("feeds", (IModel)listModel){
					  public void populateItem(final ListItem listItem)
					  {

						listItem.add((FeedContainer)listItem.getModelObject());
						
					  }
				  };
					feedListView.setVersioned(false);
					feedListView.setRenderBodyOnly(false);
					add(feedListView);
				} 
				else
				{
					feedListView.setModel(listModel);
					feedListView.modelChanged();

				}
				++i;
				
			}
		}
	}

	/**
	 * @return true if there is space free in the url list.
	 */
	public boolean spaceFree()
	{
		if(!feedUrlList.isEmpty())
		{
			if(feedUrlList.size() < maxFeeds)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		} 
		else
		{
			
			return true;
		}
	}
	
	public String removeColon(String s) {
		  StringTokenizer st = new StringTokenizer(s,":",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
	  }
	public String getHTMLID()
	{
		return this.HTMLID;
	}
	
}
