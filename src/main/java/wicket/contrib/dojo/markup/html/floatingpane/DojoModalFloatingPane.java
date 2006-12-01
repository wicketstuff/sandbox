package wicket.contrib.dojo.markup.html.floatingpane;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_MODALFLOATINGPANE;
import wicket.MarkupContainer;
import wicket.markup.ComponentTag;

public class DojoModalFloatingPane extends DojoAbstractFloatingPane
{

	public DojoModalFloatingPane(MarkupContainer parent, String id)
	{
		super(parent, id);
		add(new DojoModalFloatingPaneHandler());
		setOutputMarkupId(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_MODALFLOATINGPANE);
	}
	
}
