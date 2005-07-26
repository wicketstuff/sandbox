/**
 * Copyright (C) 2005, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base class for different kinds of databases.
 */
public abstract class Database
{
	/** Log. */
	private static Log log = LogFactory.getLog(Database.class);

	/**
	 * Drops and recreates database tables
	 */
	public abstract void formatTables();
	
	/**
	 * @return Subclass of HibernateDatabaseSession depending on type of database;
	 */
	public abstract DatabaseSession newDatabaseSession();
}
