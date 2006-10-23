package wicket.contrib.dojo.markup.html.dialog;

import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.model.Model;

/**
 * Dialog showing a Dojo dialog
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoDialog extends MarkupContainer
{

	public DojoDialog(MarkupContainer parent, String id)
	{
		super(parent, id);
		this.add(new DojoDialogHandler());
		initDojoDialog();
	}

	private void initDojoDialog()
	{
		this.setOutputMarkupId(true);
		this.add(new AttributeAppender("dojoType", new Model<String>("dialog"),""));
	}
	
	/**
	 * Set the dialog effect
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("toggle", new Model<String>(toggle.getToggle()),""));
		this.add(new AttributeAppender("toggleDuration", new Model<String>(toggle.getDuration() + ""),""));
	}


}
