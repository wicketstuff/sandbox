/**
 * Copyright (C) 2005, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

import wicket.protocol.http.WebApplication;

/**
 * The main application class for the database driven web applications.
 * 
 * @author Jonathan Locke
 */
public class DatabaseWebApplication extends WebApplication implements IDatabaseApplication
{
	/** The database for this web application */
	private Database database;

	/**
	 * Constructor
	 */
	public DatabaseWebApplication()
	{
	}

	/**
	 * @see wicket.contrib.database.IDatabaseApplication#getDatabase()
	 */
	public Database getDatabase()
	{
		return database;
	}

	/**
	 * @param database
	 *            The new database
	 */
	public void setDatabase(Database database)
	{
		this.database = database;
	}
}
