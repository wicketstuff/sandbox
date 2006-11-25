package wicket.contrib.dojo.markup.html.container;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_SPLITCONTAINER;
import wicket.MarkupContainer;
import wicket.contrib.dojo.markup.html.container.tab.DojoTabHandler;
import wicket.markup.ComponentTag;

/**
 * A split container
 * @author Vincent Demay
 *
 */
public class DojoSplitContainer extends AbstractDojoContainer
{
	/**
	 * Construct a DojoAccordionContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 * @param title container title
	 */
	public DojoSplitContainer(MarkupContainer parent, String id, String title)
	{
		super(parent, id, title);
		add(new DojoTabHandler());
	}

	/**
	 * Construct a DojoAccordionContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 */
	public DojoSplitContainer(MarkupContainer parent, String id)
	{
		this(parent, id, null);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_SPLITCONTAINER);
		tag.put("label", getTitle());
	}
}
