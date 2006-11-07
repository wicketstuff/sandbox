package wicket.contrib.dojo.markup.html.dialog;

import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.Model;

/**
 * Dialog showing a Dojo dialog
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoDialog extends WebMarkupContainer
{

	/**
	 * @param parent
	 * @param id
	 */
	public DojoDialog(MarkupContainer parent, String id)
	{
		super(parent, id);
		this.add(new DojoDialogHandler());
		this.setOutputMarkupId(true);
	}

	/**
	 * Set the dialog effect
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("toggle", new Model<String>(toggle.getToggle()),""));
		this.add(new AttributeAppender("toggleDuration", new Model<String>(toggle.getDuration() + ""),""));
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("dojoType", "dialog");
		tag.put("style", "display:none");
	}


}
