/*
 * $Id: DatabaseDao.java 245 2005-08-18 18:51:38 +0000 (Thu, 18 Aug 2005)
 * jonathanlocke $ $Revision$ $Date: 2005-08-18 18:51:38 +0000 (Thu, 18
 * Aug 2005) $
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
 * Abstract base class for Database DAOs
 * 
 * @author Jonathan Locke
 */
public abstract class DatabaseDao
{
	/**
	 * Any session to use. If null, DatabaseSession.get() will be called to get
	 * the database session for the current web request.
	 */
	private DatabaseSession session;

	/**
	 * Constructor
	 */
	public DatabaseDao()
	{
	}

	/**
	 * This method can be called when a DatabaseDao is being used outside the
	 * context of a web application. For example, when populating a database.
	 * 
	 * @param session
	 *            The session for this DAO.
	 */
	public void setSession(final DatabaseSession session)
	{
		this.session = session;

	}

	/**
	 * @return Database session
	 */
	public DatabaseSession getSession()
	{
		if (session != null)
		{
			return session;
		}
		return DatabaseSession.get();
	}
}
