/**
 * Copyright (C) 2006, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

import java.io.Serializable;

/**
 * Basic properties of all database objects.
 *
 * @author Jonathan Locke
 */
public interface IDatabaseObject extends IDatabaseObjectLifecycle, Serializable
{
	/**
	 * @return Id of database object
	 */
	public Long getId();
}
