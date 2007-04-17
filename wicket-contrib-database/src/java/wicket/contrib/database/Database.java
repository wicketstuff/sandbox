/*
 * $Id: Database.java 751 2006-06-02 00:04:01 +0000 (Fri, 02 Jun 2006)
 * jonathanlocke $ $Revision$ $Date: 2006-06-02 00:04:01 +0000 (Fri, 02
 * Jun 2006) $
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

/**
 * Base class for different kinds of databases.
 */
public abstract class Database
{
	/**
	 * Name of this database
	 */
	private final String name;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            Name of database
	 */
	public Database(final String name)
	{
		this.name = name;
	}

	/**
	 * Drops and recreates database tables
	 */
	public abstract void format();

	/**
	 * @return Returns name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return Subclass of HibernateDatabaseSession depending on type of
	 *         database;
	 */
	public abstract DatabaseSession newDatabaseSession();
}
