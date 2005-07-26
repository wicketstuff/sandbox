/*
 * $Id$
 * $Revision$ $Date$
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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.impl.SessionFactoryImpl;

import wicket.WicketRuntimeException;
import wicket.contrib.database.AbstractEntity;
import wicket.contrib.database.Database;
import wicket.contrib.database.DatabaseSession;


/**
 * Utility that sets up the database.
 */
public class HibernateDatabase extends Database
{
	/** Log. */
	private static Log log = LogFactory.getLog(HibernateDatabase.class);

	/** The one and only Hibernate session factory for this web application. */
	private SessionFactory hibernateSessionFactory;

	/** The configuration for this Hibernate database */
	private Configuration configuration;

	/**
	 * Construct.
	 * 
	 * @param configuration
	 *            Hibernate configuration information
	 */
	public void configure(final Configuration configuration)
	{
		this.configuration = configuration;
		try
		{
			// Build hibernate SessionFactory for this application
			hibernateSessionFactory = configuration.buildSessionFactory();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see wicket.contrib.database.Database#newDatabaseSession()
	 */
	public DatabaseSession newDatabaseSession()
	{
		return new HibernateDatabaseSession(hibernateSessionFactory.openSession());
	}

	/**
	 * @return Default annotation configuration for a Hibernate 3 database
	 */
	protected AnnotationConfiguration newAnnotationConfiguration()
	{
		final AnnotationConfiguration configuration = new AnnotationConfiguration();
		configuration.addAnnotatedClass(AbstractEntity.class);
		return configuration;
	}

	/**
	 * Drops and recreates all hibernate tables
	 */
	public void formatTables()
	{
		Session session = null;
		try
		{
			SessionFactory sessionFactory = configuration.buildSessionFactory();
			Dialect dialect = ((SessionFactoryImpl)sessionFactory).getDialect();
			final String[] drops = configuration.generateDropSchemaScript(dialect);
			final String[] creates = configuration.generateSchemaCreationScript(dialect);
			session = sessionFactory.openSession();
			final Connection connection = session.connection();
			final Statement statement = connection.createStatement();
			executeStatements(connection, statement, splitAlterTables(drops, true));
			executeStatements(connection, statement, splitAlterTables(drops, false));
			executeStatements(connection, statement, creates);
		}
		catch (HibernateException e)
		{
			throw new WicketRuntimeException(e);
		}
		catch (SQLException e)
		{
			throw new WicketRuntimeException(e);
		}
		finally
		{
			try
			{
				if (session != null)
				{
					session.close();
				}
			}
			catch (HibernateException e)
			{
				throw new WicketRuntimeException(e);
			}
		}
	}

	/**
	 * Filter statements on start of statement.
	 * 
	 * @param drops
	 *            statements
	 * @param includeAlterFlag
	 *            if true, everything that starts with alter, else the inverse
	 * @return part of the input
	 */
	private String[] splitAlterTables(String[] drops, boolean includeAlterFlag)
	{
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < drops.length; i++)
		{
			if (includeAlterFlag)
			{
				if (drops[i].toLowerCase().trim().startsWith("alter"))
				{
					temp.add(drops[i]);
				}
			}
			else
			{
				if (!drops[i].toLowerCase().trim().startsWith("alter"))
				{
					temp.add(drops[i]);
				}
			}
		}
		return (String[])temp.toArray(new String[temp.size()]);
	}

	/**
	 * Execute statements.
	 * 
	 * @param connection
	 *            connection
	 * @param statement
	 *            statement object
	 * @param statements
	 *            statements
	 * @throws SQLException
	 *             sql error
	 */
	private void executeStatements(final Connection connection, final Statement statement,
			final String[] statements) throws SQLException
	{
		for (int i = 0; i < statements.length; i++)
		{
			log.info("exec: " + statements[i]);
			try
			{
				statement.executeUpdate(statements[i]);
				connection.commit();
			}
			catch (SQLException e)
			{
				log.error(e.getMessage());
			}
		}
	}
}
