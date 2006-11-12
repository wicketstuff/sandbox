package wicket.contrib.dojo.markup.html.dialog;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_DIALOG;
import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.contrib.dojo.widgets.HideWebMarkupContainer;
import wicket.markup.ComponentTag;
import wicket.model.Model;

/**
 * Dialog showing a Dojo dialog
 * @author vdemay
 *
 */
public class DojoDialog extends HideWebMarkupContainer
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
		tag.put(DOJO_TYPE, DOJO_TYPE_DIALOG);
	}
}
