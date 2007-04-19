/*
 * $Id: HypersonicHibernateDatabase.java,v 1.5 2005/02/22 17:42:33 jonathanlocke
 * Exp $ $Revision$ $Date$
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
package wicket.contrib.database.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.Server;

import org.apache.wicket.util.time.Duration;

/**
 * A hibernate database that automatically uses an embedded Hypersonic SQL
 * server.
 * 
 * @author Jonathan Locke
 */
public class HypersonicHibernateDatabase extends HibernateDatabase
{
	/** Log. */
	private static Log log = LogFactory.getLog(HypersonicHibernateDatabase.class);

	/**
	 * Constructor
	 * 
	 * @param name
	 *            Name of database
	 */
	public HypersonicHibernateDatabase(final String name)
	{
		super(name);
		start();
	}

	/**
	 * Starts running HSQL server
	 */
	private void start()
	{		
		// Create Server object
		final Server server = new Server();		
		server.setNoSystemExit(true);
		server.setRestartOnShutdown(false);
		
		// If the database server isn't already online 
		if (!server.getStateDescriptor().equals("ONLINE"))
		{
			// Start a new server
			log.info("Starting hypersonic database");
			server.start();
			
			// Wait for it to come online
			while (!server.getStateDescriptor().equals("ONLINE"))
			{
				Duration.seconds(0.1).sleep();
			}
			log.info("Database ready");
		}
	}
}
