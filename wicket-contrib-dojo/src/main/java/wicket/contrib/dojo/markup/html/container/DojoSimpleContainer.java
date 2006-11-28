package wicket.contrib.dojo.markup.html.container;

import wicket.contrib.dojo.DojoIdConstants;
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
	public DojoSimpleContainer(String id, String title)
	{
		super(id, title);
		add(new DojoSimpleContainerHandler());
	}

	/**
	 * Construct a Dojo container
	 * @param parent parent where container will be added
	 * @param id container id
	 */
	public DojoSimpleContainer(String id)
	{
		this(id, null);
	}
	
	/**
	 * add attributes on component tag
	 * @param tag 
	 */
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_CONTENTPANE);
		tag.put("label", getTitle());
	}

}
