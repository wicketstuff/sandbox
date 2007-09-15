package wicketstuff.crud.view;

import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.model.IModel;

/**
 * Border around form-body in edit screen
 * 
 * @author igor.vaynberg
 * 
 */
public class FormBorder extends Border
{
	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public FormBorder(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 */
	public FormBorder(String id, IModel model)
	{
		super(id, model);
	}

}
