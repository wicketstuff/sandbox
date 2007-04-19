/*
 * $Id$ $Revision:
 * 1.43 $ $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.database;

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * A model class for objects persisted in a relational database that is keyed
 * using an object identifier.
 * 
 * @author Jonathan Locke
 */
public class DatabaseObjectModel extends LoadableDetachableModel
{
	private static final long serialVersionUID = 1L;

	/** The class of object */
	private Class c;

	/** The id for the object stored in the database */
	private Long id;

	public DatabaseObjectModel(final Class c, final Long id)
	{
		this.c = c;
		this.id = id;
	}

	/**
	 * Called to attach the model using the database session when the object is
	 * needed.
	 * 
	 * @see wicket.model.LoadableDetachableModel#load()
	 */
	public Object load()
	{
		return DatabaseSession.get().load(c, id);
	}
}
