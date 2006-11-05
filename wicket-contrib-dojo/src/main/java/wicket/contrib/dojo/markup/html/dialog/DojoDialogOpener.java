package wicket.contrib.dojo.markup.html.dialog;

import wicket.AttributeModifier;
import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.markup.html.link.Link;
import wicket.model.Model;

/**
 * Link to open a DojoDialog
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoDialogOpener extends Link{

	public DojoDialogOpener(MarkupContainer parent, String id, DojoDialog dialog)
	{
		super(parent, id);
		String dialogId = dialog.getMarkupId();
		String onClick = "";
		onClick = "javascript:getDialog('" + dialogId + "').show(); return false;";
		this.add(new AttributeAppender("onClick", new Model<String>(onClick),""));
		this.add(new AttributeModifier("href", new Model<String>("#")));
	}

	@Override
	public void onClick()
	{
		//DO NOTHING
	}

}
