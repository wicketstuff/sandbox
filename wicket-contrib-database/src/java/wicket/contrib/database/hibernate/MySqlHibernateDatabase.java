/*
 * $Id: MySqlHibernateDatabase.java,v 1.5 2005/02/22 17:42:33 jonathanlocke
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
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * A hibernate database that automatically uses an embedded MySql SQL
 * server.
 * 
 * @author Jonathan Locke
 */
public class MySqlHibernateDatabase extends HibernateDatabase
{
	/** Log. */
	private static Log log = LogFactory.getLog(MySqlHibernateDatabase.class);

	/**
	 * Constructor
	 */
	public MySqlHibernateDatabase()
	{
	}

	/**
	 * @return Default annotation configuration for a MySql hibernate 3
	 *         database
	 */
	protected AnnotationConfiguration newAnnotationConfiguration()
	{
		final AnnotationConfiguration configuration = super.newAnnotationConfiguration();
		configuration.setProperty("hibernate.show_sql", "true");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/voicetribe");
		configuration.setProperty("hibernate.connection.username", "root");
		configuration.setProperty("hibernate.connection.password", "");
		// configuration.setProperty("c3p0.min_size", "3");
		// configuration.setProperty("c3p0.max_size", "5");
		// configuration.setProperty("c3p0.timeout", "1800");
		return configuration;
	}
}
