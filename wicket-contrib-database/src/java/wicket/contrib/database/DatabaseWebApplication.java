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

import org.apache.wicket.protocol.http.WebApplication;

/**
 * The main application class for the database driven web applications.
 * 
 * @author Jonathan Locke
 */
public abstract class DatabaseWebApplication extends WebApplication implements IDatabaseApplication
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
