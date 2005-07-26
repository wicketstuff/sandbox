/**
 * Copyright (C) 2005, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

import wicket.model.LoadableDetachableModel;

/**
 * A model class for objects persisted in a relational database that is keyed
 * using an object identifier.
 * 
 * @author Jonathan Locke
 */
public class DatabaseObjectModel extends LoadableDetachableModel
{
	/** The class of object */
	private Class c;

	/** The id for the object stored in the database */
	private Long id;

	/** The session for this database object model */
	private DatabaseSession session;

	public DatabaseObjectModel(final DatabaseSession session, final Class c, final Long id)
	{
		this.session = session;
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
		return session.load(c, id);
	}
}
