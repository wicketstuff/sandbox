package wicket.contrib.dojo.examples.rssreader.model;

import java.util.ArrayList;
import java.util.List;

public class FeedListMaker {

	private static List<FeedModel> feedList;
	
	public static void createFeedList(){
		feedList = new ArrayList<FeedModel>();
		feedList.add(new FeedModel("BBC","http://news.bbc.co.uk/rss/newsonline_uk_edition/front_page/rss091.xml","img/bbc.gif"));
		feedList.add(new FeedModel("New York Times","http://www.nytimes.com/services/xml/rss/userland/HomePage.xml","img/NYTimes.gif"));
		feedList.add(new FeedModel("EnGadget","http://www.engadget.com/rss.xml","img/engadget.gif"));
		feedList.add(new FeedModel("Reuters","http://www.microsite.reuters.com/rss/topNews","img/reuters.gif"));
		feedList.add(new FeedModel("Slashdot","http://slashdot.org/index.rss","img/slashdot.gif"));
		feedList.add(new FeedModel("Yahoo Entertainment","http://rss.news.yahoo.com/rss/entertainment","img/yahoo.gif"));
		feedList.add(new FeedModel("The Daily WTF","http://thedailywtf.com/rss.aspx","img/dwtf.gif"));
		feedList.add(new FeedModel("Kotaku","http://feeds.gawker.com/kotaku/full","img/kotaku.gif"));
	}
	
	public static List<FeedModel> getList(){
		return feedList;
	}
	
	public static void addInList(FeedModel model){
		feedList.add(model);
	}
}
