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
	public HideWebMarkupContainer(String id, IModel model)
	{
		super(id, model);
	}

	/**
	 * @param parent
	 * @param id
	 */
	public HideWebMarkupContainer(String id)
	{
		super(id);
	}


	protected void onStyleAttribute(StyleAttribute styleAttribute)
	{
		super.onStyleAttribute(styleAttribute);
		setDisplay("none");
	}

}
