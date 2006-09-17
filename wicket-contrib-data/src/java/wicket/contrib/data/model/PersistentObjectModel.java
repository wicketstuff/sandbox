/*
 * $Id$ $Revision$ $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.data.model;

import java.io.Serializable;

import wicket.model.AbstractDetachableModel;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * {@link wicket.model.IModel}that supports a (persistent) object that has a
 * unique id, and that is to be loaded when the model is attached.
 * </p>
 * <p>
 * The actual loading of the object is delegated to the select object action.
 * </p>
 * 
 * @author Eelco Hillenius
 */
public class PersistentObjectModel<T, V> extends AbstractDetachableModel<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Transient flag to prevent multiple detach/attach scenario. We need to
	 * maintain this flag as we allow 'null' model values!
	 */
	private transient boolean attached = false;

	/** model that provides the object id. */
	private IModel<V> idModel;

	/** The (temporary) detail object. */
	private transient T object;

	/** action that loads the object. */
	private ISelectObjectAction<T, V> selectObjectAction;

	/**
	 * Construct with a model that provides the id.
	 * 
	 * @param idModel
	 *            the model that provides the id
	 * @param selectObjectAction
	 *            action that loads the object
	 */
	public PersistentObjectModel(IModel<V> idModel,
			ISelectObjectAction<T, V> selectObjectAction)
	{
		if (idModel == null) // consider same as null id
		{
			this.idModel = new Model<V>(null);
		}
		else
		{
			this.idModel = idModel;
		}
		this.selectObjectAction = selectObjectAction;
	}

	/**
	 * Construct with a model that provides the id.
	 * 
	 * @param selectObjectAction
	 *            action that loads the object
	 */
	public PersistentObjectModel(ISelectObjectAction<T, V> selectObjectAction)
	{
		this(new Model<V>(null), selectObjectAction);
	}

	/**
	 * Construct with an id.
	 * 
	 * @param id
	 *            the object's id; will be wrapped in a {@link Model}
	 * @param selectObjectAction
	 *            action that loads the object
	 */
	public PersistentObjectModel(V id, ISelectObjectAction<T, V> selectObjectAction)
	{
		this.idModel = new Model<V>(id);
		this.selectObjectAction = selectObjectAction;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public final V getId()
	{
		V id = idModel.getObject();
		return id;
	}

	/**
	 * Gets the model that provides the object id.
	 * 
	 * @return the model that provides the object id
	 */
	public final IModel<V> getIdModel()
	{
		return idModel;
	}

	/**
	 * Whether this model has been attached to the current request.
	 * 
	 * @return whether this model has been attached to the current request
	 */
	@Override
	public final boolean isAttached()
	{
		return attached;
	}

	/**
	 * Loads the object using the given id.
	 * 
	 * @param id
	 *            the id of the object to load
	 * @return the loaded object or null if not found (or - if that's what you
	 *         prefer - throw an exception if the object is not found)
	 */
	public T loadObject(V id)
	{
		return selectObjectAction.execute(id);
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id
	 */
	public final void setId(V id)
	{
		idModel.setObject(id);
		detach();
	}

	/**
	 * Sets idModel.
	 * 
	 * @param idModel
	 *            idModel
	 */
	public void setIdModel(IModel<V> idModel)
	{
		this.idModel = idModel;
		detach();
	}

	/**
	 * Sets action that loads the object.
	 * 
	 * @param selectObjectAction
	 *            action that loads the object.
	 */
	public final void setSelectObjectAction(ISelectObjectAction<T, V> selectObjectAction)
	{
		this.selectObjectAction = selectObjectAction;
	}

	/**
	 * Gets the action that loads the object.
	 * 
	 * @return the action that loads the object
	 */
	protected final ISelectObjectAction<T, V> getSelectObjectAction()
	{
		return selectObjectAction;
	}

	/**
	 * Attach to the current request. Does nothing; Override this method if you
	 * need to do more than the default behaviour.
	 */
	@Override
	protected void onAttach()
	{
		final V id = getId();
		if ((id != null) && (!(id instanceof Serializable)))
		{
			throw new IllegalArgumentException("id must be serializable");
		}
		this.object = loadObject(id);
	}

	/**
	 * Detach from the current request. Does nothing; Override this method if
	 * you need to do more than the default behaviour.
	 */
	@Override
	protected void onDetach()
	{
		this.object = null;
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onGetObject()
	 */
	@Override
	protected T onGetObject()
	{
		return object;
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onSetObject(java.lang.Object)
	 */
	@Override
	protected void onSetObject(Object T)
	{
		throw new UnsupportedOperationException(
				"an object can only be set through its id");
	}
}
