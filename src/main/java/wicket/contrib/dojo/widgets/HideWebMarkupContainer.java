package wicket.contrib.dojo.widgets;

import wicket.MarkupContainer;
import wicket.model.IModel;

/**
 * Web container hidden by default
 * @author vdemay
 *
 */
public class HideWebMarkupContainer extends StylingWebMarkupContainer
{

	/**
	 * @param parent
	 * @param id
	 * @param model
	 */
	public HideWebMarkupContainer(MarkupContainer parent, String id, IModel model)
	{
		super(parent, id, model);
	}

	/**
	 * @param parent
	 * @param id
	 */
	public HideWebMarkupContainer(MarkupContainer parent, String id)
	{
		super(parent, id);
	}

	@Override
	protected void onStyleAttribute(StyleAttribute styleAttribute)
	{
		super.onStyleAttribute(styleAttribute);
		setDisplay("none");
	}

}
