package wicket.contrib.dojo.examples.rssreader;



import wicket.MarkupContainer;
import wicket.contrib.dojo.examples.AutoUpdatePage2;
import wicket.contrib.markup.html.tooltip.SimpleTooltip;
import wicket.contrib.markup.html.tooltip.Tooltip;
import wicket.markup.html.image.Image;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;

/**
 * Custom pannel which adds a feed to MainContainer.
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 *
 */
public class AddPanel extends Panel{

	private final MainContainer main;
	
	public AddPanel(MarkupContainer parent, String id, String title,String url, String imgpath,  MainContainer main) {
		super(parent, id);
		this.main = main;
		new Image(this, "addimg", new Model(imgpath));
		new Tooltip(this, "addtooltip", new SimpleTooltip(this, this, title));
		AddFeedHandler afh = new AddFeedHandler(url);
		add(afh);
		
	}
	
	public MainContainer getMain()
	{
		return this.main;
	}

	public int getIndex()
	{
		return Integer.parseInt(((AutoUpdatePage2)getPage()).getFeedPicker());
	}

	
}
