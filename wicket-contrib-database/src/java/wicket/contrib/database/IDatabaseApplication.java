/**
 * Copyright (C) 2005, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

/**
 * Interface for database driven applications. The database for the application
 * can be retrived by calling getDatabase().
 * 
 * @author Jonathan Locke
 */
public interface IDatabaseApplication
{
	/**
	 * @return The application's database
	 */
	public Database getDatabase();
}
