package wicket.contrib.dojo.examples;

import java.util.List;

import wicket.Component;
import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.examples.rssreader.model.FeedListMaker;
import wicket.contrib.dojo.examples.rssreader.model.FeedModel;
import wicket.contrib.dojo.examples.rssreader.widgets.FeedPanel;
import wicket.contrib.dojo.markup.html.dialog.DojoDialog;
import wicket.contrib.dojo.markup.html.dialog.DojoDialogCloser;
import wicket.contrib.dojo.markup.html.dialog.DojoDialogOpener;
import wicket.contrib.dojo.markup.html.floatingpane.DojoFloatingPane;
import wicket.contrib.dojo.markup.html.tooltip.DojoTooltip;
import wicket.contrib.dojo.toggle.DojoExplodeToggle;
import wicket.contrib.dojo.toggle.DojoWipeToggle;
import wicket.contrib.dojo.update.DojoUpdateHandler;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.Model;

public class RssReader extends WebPage {
	
	
	public RssReader()
	{
		final Label loading = new Label(this, "loading","Loading ...");
		
		//a list with all selectable feeds
		FeedListMaker.createFeedList();
		ListView<FeedModel> feedList = new ListView(this, "feedList", FeedListMaker.getList()){
			protected void populateItem(ListItem item) {
				Label label = new Label(item, "name", ((FeedModel)item.getModelObject()).getName());
				DojoTooltip tooltip = new DojoTooltip(item,"tooltip",label);
				tooltip.setToggle(new DojoExplodeToggle(200));
				new Image(item, "image", new ResourceReference(RssReader.class, "rssreader/" + ((FeedModel)item.getModelObject()).getImage()));
				final String url = ((FeedModel)item.getModelObject()).getUrl();
				label.add(new DojoUpdateHandler("onclick", loading){
					public void updateComponents(AjaxRequestTarget target, Component submitter, String value) {
						FeedPanel feed = (FeedPanel) ((WebMarkupContainer)getPage().get("window")).get("rss");
						feed = new FeedPanel(((WebMarkupContainer)getPage().get("window")), "rss", new Model<String>(url));
						target.addComponent(feed);
					}
				});
			}
		};
		
		WebMarkupContainer window = new WebMarkupContainer(this, "window");
		FeedPanel feed = new FeedPanel(window, "rss", new Model<String>("http://www.demay-fr.net/blog/rss.php?type=co"));
		feed.setOutputMarkupId(true);
		
		//about
		DojoDialog about = new DojoDialog(this, "about");
		about.setToggle(new DojoWipeToggle(200));
		new DojoDialogOpener(this, "open", about);
		new DojoDialogCloser(about, "close", about);
		
		new Image(this, "logo");
	}
	
	
}


