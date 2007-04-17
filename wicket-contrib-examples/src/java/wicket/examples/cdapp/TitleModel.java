package wicket.examples.cdapp;

import wicket.contrib.data.model.PersistentObjectModel;
import wicket.examples.cdapp.model.CD;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * Special model for the title header. It returns the CD title if there's a
 * loaded object (when the id != null) or it returns a special string in case
 * there is no loaded object (if id == null).
 */
public class TitleModel implements IModel
{
	/** decorated model; provides the current id. */
	private final PersistentObjectModel cdModel;

	private transient boolean attached = false;

	/**
	 * Construct.
	 * 
	 * @param cdModel
	 *            the model to decorate
	 */
	public TitleModel(PersistentObjectModel cdModel)
	{
		this.cdModel = cdModel;
	}

	/**
	 * Attach for request.
	 */
	public void attach()
	{
		cdModel.attach();
	}

	/**
	 * @see Model#detach()
	 */
	public void detach()
	{
		cdModel.detach();
		attached = false;
	}

	/**
	 * @see wicket.model.IModel#getObject()
	 */
	public Object getObject()
	{
		if (!attached)
		{
			attach();
			attached = true;
		}
		if (cdModel.getId() != null) // it is allready persistent
		{
			CD cd = (CD)cdModel.getObject();
			return cd.getTitle();
		}
		else
		// it is a new cd
		{
			return "<NEW CD>";
		}
	}

	/**
	 * @see IModel#setObject(Object)
	 */
	public void setObject(final Object object)
	{
		cdModel.setObject(object);
	}
}