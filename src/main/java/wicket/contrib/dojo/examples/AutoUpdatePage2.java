package wicket.contrib.dojo.examples;

import java.util.Arrays;
import java.util.List;

import wicket.AttributeModifier;
import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.contrib.dojo.dojofx.FXOnClickExploder;
import wicket.contrib.dojo.examples.rssreader.AddPanel;
import wicket.contrib.dojo.examples.rssreader.DescriptionModel;
import wicket.contrib.dojo.examples.rssreader.DescriptionPanel;
import wicket.contrib.dojo.examples.rssreader.MainContainer;
import wicket.contrib.dojo.examples.rssreader.UpdateLabel;
import wicket.contrib.markup.html.form.ImmediateRadioChoice;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.RadioChoice;
import wicket.markup.html.image.Image;
import wicket.model.CompoundPropertyModel;
import wicket.model.Model;
import wicket.model.PropertyModel;

public class AutoUpdatePage2 extends WebPage {
	private String text;
	private AddPanel addPanel1;
	private AddPanel addPanel2;
	private AddPanel addPanel3;
	private AddPanel addPanel4;
	private AddPanel addPanel5;
	private AddPanel addPanel6;
	private AddPanel addPanel7;
	private AddPanel addPanel8;
	DojoAjaxHandler ajax;
	private Image testImage;
	
	/** available numbers for the radio selection. */
	private static final List NUMBERS = Arrays.asList(new String[] { "1", "2", "3", "4" });
	private String feedPicker = "1";
	
	UpdateLabel j;
	Label loading;
	WebMarkupContainer c;
	DescriptionPanel dpanel;
	public AutoUpdatePage2()
	{
		
				
		loading = new Label("loading", new Model("Loading......"));
		loading.add(new AttributeModifier("id", true, new Model("loading_node")));
		add(loading);
			
		WebMarkupContainer wmc = new WebMarkupContainer("temp");
		DescriptionModel dmodel = new DescriptionModel("Click a news item to view it's description.");
		dpanel = new DescriptionPanel("dpanel", new CompoundPropertyModel(dmodel));
		wmc.add(dpanel);
		add(wmc);
		
		MainContainer main = new MainContainer("mainContainer", dpanel);

		main.addFeed("http://www.nu.nl/deeplink_rss2/index.jsp?r=Algemeen");
		main.addFeed("http://thedailywtf.com/rss.aspx");
		main.addFeed("http://slashdot.org/index.rss");
		main.addFeed("http://www.engadget.com/rss.xml");
		
		add(main);
		
		RadioChoice rc = new ImmediateRadioChoice("feedPicker", new PropertyModel(AutoUpdatePage2.this, "feedPicker"),NUMBERS).setSuffix("");
		rc.setLabel(new Model("Position to add feed"));
		add(rc);

		addPanel1 = new AddPanel("addPanel1", "BBC","http://news.bbc.co.uk/rss/newsonline_uk_edition/front_page/rss091.xml","img/bbc.gif", main); 
		addPanel2 = new AddPanel("addPanel2", "New York Times","http://www.nytimes.com/services/xml/rss/userland/HomePage.xml","img/NYTimes.gif", main);
		addPanel3 = new AddPanel("addPanel3", "EnGadget","http://www.engadget.com/rss.xml","img/engadget.gif", main);
		addPanel4 = new AddPanel("addPanel4", "Reuters","http://www.microsite.reuters.com/rss/topNews","img/reuters.gif", main);
		addPanel5 = new AddPanel("addPanel5", "Slashdot","http://slashdot.org/index.rss","img/slashdot.gif", main);
		addPanel6 = new AddPanel("addPanel6", "Yahoo Entertainment","http://rss.news.yahoo.com/rss/entertainment","img/yahoo.gif", main);
		addPanel7 = new AddPanel("addPanel7", "The Daily WTF","http://thedailywtf.com/rss.aspx","img/dwtf.gif", main);
		addPanel8 = new AddPanel("addPanel8", "Kotaku","http://feeds.gawker.com/kotaku/full","img/kotaku.gif", main);
		
		
		add(addPanel1);
		add(addPanel2);
		add(addPanel3);
		add(addPanel4);
		add(addPanel5);
		add(addPanel6);
		add(addPanel7);
		add(addPanel8);
		
		Image about = new Image("about", new Model("img/AboutButton.gif"));
		add(about);
		WebMarkupContainer table2;
		Image close;
		add(table2 = new WebMarkupContainer("abouttable"));

		table2.add(new Image("aboutimg", "img/DojoLogo2.gif"));
		table2.add(close = new Image("close", new Model("img/close.gif")));
		FXOnClickExploder d = new FXOnClickExploder(400, about);
		table2.add(d);
		d.addTrigger(close);
			
	}
	
	public String getFeedPicker()
	{
		return this.feedPicker;
	}
	
	public void setFeedPicker(String index)
	{
		this.feedPicker = index;
	}
	
}


