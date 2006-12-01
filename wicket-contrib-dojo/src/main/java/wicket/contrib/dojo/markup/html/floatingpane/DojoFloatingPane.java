package wicket.contrib.dojo.markup.html.floatingpane;

import wicket.contrib.dojo.DojoIdConstants;
import wicket.markup.ComponentTag;

/**
 * @author vdemay
 *
 */
public class DojoFloatingPane extends DojoAbstractFloatingPane
{

	private boolean constrainToContainer;
	
	/**
	 * @param parent
	 * @param id
	 */
	public DojoFloatingPane(String id)
	{
		super(id);
		add(new DojoFloatingPaneHandler());
		constrainToContainer = true;
	}
	
	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_FLOATINGPANE);
	}
}
