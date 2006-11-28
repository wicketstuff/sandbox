package wicket.contrib.dojo.markup.html.container.accordion;

import wicket.MarkupContainer;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.contrib.dojo.markup.html.container.AbstractDojoContainer;
import wicket.markup.ComponentTag;

/**
 * An accordion container
 * @author Vincent Demay
 *
 */
public class DojoAccordionContainer extends AbstractDojoContainer
{

	/**
	 * Construct a DojoAccordionContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 * @param title container title
	 */
	public DojoAccordionContainer(String id, String title)
	{
		super(id, title);
		add(new DojoAccordionHandler());
	}

	/**
	 * Construct a DojoAccordionContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 */
	public DojoAccordionContainer(String id)
	{
		this(id, null);
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_ACCORDIONCONTAINER);
		tag.put("label", getTitle());
	}

	/**
	 * Triggered when change selection
	 *
	 */
	public void onChange()
	{
		
	}
}
