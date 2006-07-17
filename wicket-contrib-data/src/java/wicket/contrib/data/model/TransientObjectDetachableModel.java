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

import wicket.model.AbstractDetachableModel;

/**
 * Model that makes working with detachable models a breeze again. Holds a
 * temporary, transient model object, that is set on 'onAttach' by calling
 * abstract method 'load', and that will be reset/ set to null on 'onDetach'.
 * 
 * A usage example:
 * 
 * <pre>
 * IModel&lt;List&lt;Venue&gt;&gt; venueListModel = new TransientObjectDetachableModel&lt;List&lt;Venue&gt;&gt;()
 * {
 * 	protected List&lt;Venue&gt; load()
 * 	{
 * 		return getVenueDao().findVenues();
 * 	}
 * };
 * </pre>
 * 
 * @author Eelco Hillenius
 */
public abstract class TransientObjectDetachableModel<T> extends
		AbstractDetachableModel<T>
{
	/** temporary, transient object. */
	private transient T tempModelObject;

	/**
	 * Construct.
	 */
	public TransientObjectDetachableModel()
	{
		super();
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onAttach()
	 */
	@Override
	protected final void onAttach()
	{
		this.setObject(load());
	}

	/**
	 * Loads and returns the (temporary) model object.
	 * 
	 * @return the (temporary) model object
	 */
	protected abstract T load();

	/**
	 * @see wicket.model.AbstractDetachableModel#onDetach()
	 */
	@Override
	protected final void onDetach()
	{
		tempModelObject = null;
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onGetObject()
	 */
	@Override
	protected final T onGetObject()
	{
		return tempModelObject;
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onSetObject(java.lang.Object)
	 */
	@Override
	protected final void onSetObject(T object)
	{
		this.tempModelObject = object;
	}
}
