package wicket.contrib.dojo.markup.html.container.accordion;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_ACCORDIONCONTAINER;
import wicket.MarkupContainer;
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
	public DojoAccordionContainer(MarkupContainer parent, String id, String title)
	{
		super(parent, id, title);
		add(new DojoAccordionHandler());
	}

	/**
	 * Construct a DojoAccordionContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 */
	public DojoAccordionContainer(MarkupContainer parent, String id)
	{
		this(parent, id, null);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_ACCORDIONCONTAINER);
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
