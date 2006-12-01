package wicket.contrib.dojo.markup.html.floatingpane;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_FLOATINGPANE;
import wicket.MarkupContainer;
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
	public DojoFloatingPane(MarkupContainer parent, String id)
	{
		super(parent, id);
		add(new DojoFloatingPaneHandler());
		constrainToContainer = true;
	}
	
	
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_FLOATINGPANE);
	}
}
