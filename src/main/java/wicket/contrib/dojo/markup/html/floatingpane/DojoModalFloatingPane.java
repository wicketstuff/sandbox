package wicket.contrib.dojo.markup.html.floatingpane;

import wicket.contrib.dojo.DojoIdConstants;
import wicket.markup.ComponentTag;

public class DojoModalFloatingPane extends DojoAbstractFloatingPane
{

	public DojoModalFloatingPane(String id)
	{
		super(id);
		add(new DojoModalFloatingPaneHandler());
		setOutputMarkupId(true);
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_MODALFLOATINGPANE);
	}
	
}
