package wicket.contrib.dojo.markup.html.container;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_CONTENTPANE;
import wicket.MarkupContainer;
import wicket.markup.ComponentTag;

/**
 * A simple Dojo container
 * @author Vincent Demay
 *
 */
public class DojoSimpleContainer extends AbstractDojoContainer
{
	/**
	 * Construct a Dojo container
	 * @param parent parent where container will be added
	 * @param id container id
	 * @param title container title
	 */
	public DojoSimpleContainer(MarkupContainer parent, String id, String title)
	{
		super(parent, id, title);
		add(new DojoSimpleContainerHandler());
	}

	/**
	 * Construct a Dojo container
	 * @param parent parent where container will be added
	 * @param id container id
	 */
	public DojoSimpleContainer(MarkupContainer parent, String id)
	{
		this(parent, id, null);
	}
	
	/**
	 * add attributes on component tag
	 * @param tag 
	 */
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_CONTENTPANE);
		tag.put("label", getTitle());
	}

}
