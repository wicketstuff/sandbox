package wicket.contrib.dojo.markup.html.tabs;

import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.Model;

/**
 * Dialog showing a Dojo tab panel
 * DojoTabs should be added 
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoTabPanel extends WebMarkupContainer
{

	/**
	 * @param parent
	 * @param id
	 */
	public DojoTabPanel(MarkupContainer parent, String id)
	{
		super(parent, id);
		add(new DojoTabHandler());
		this.setOutputMarkupId(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("dojoType", "TabContainer");
	}
	
	public void setSelectedTab(DojoTab tab){
		
	}
	
	public DojoTab getSelectedTab(){
		return null;
	}


}
