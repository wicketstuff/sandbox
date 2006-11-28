package wicket.contrib.dojo.markup.html.container;

import wicket.contrib.dojo.DojoIdConstants;
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
	public DojoSplitContainer(String id, String title)
	{
		super(id, title);
		add(new DojoTabHandler());
	}

	/**
	 * Construct a DojoAccordionContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 */
	public DojoSplitContainer( String id)
	{
		this(id, null);
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_SPLITCONTAINER);
		tag.put("label", getTitle());
	}
}
