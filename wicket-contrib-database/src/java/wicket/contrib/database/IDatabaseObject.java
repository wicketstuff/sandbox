/**
 * Copyright (C) 2006, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

/**
 * Basic properties of all database objects.
 *
 * @author Jonathan Locke
 */
public interface IDatabaseObject extends IDatabaseObjectLifecycle
{
	/**
	 * @return Id of database object
	 */
	public Long getId();
}
