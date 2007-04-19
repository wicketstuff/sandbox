/*
 * $Id: HibernateDatabase.java 724 2006-05-16 20:49:45 +0000 (Tue, 16 May 2006)
 * jonathanlocke $ $Revision$ $Date: 2006-05-16 20:49:45 +0000 (Tue, 16
 * May 2006) $
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

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.type.Type;

import org.apache.wicket.WicketRuntimeException;
import wicket.contrib.database.Database;
import wicket.contrib.database.DatabaseSession;
import wicket.contrib.database.IDatabaseObject;

/**
 * Utility that sets up the database.
 */
public class HibernateDatabase extends Database
{
	/** Log. */
	private static Log log = LogFactory.getLog(HibernateDatabase.class);

	/** The configuration for this Hibernate database */
	private Configuration configuration;

	/** The one and only Hibernate session factory for this web application. */
	private SessionFactory hibernateSessionFactory;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            Name of database
	 */
	public HibernateDatabase(final String name)
	{
		super(name);
	}

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
	 * Updates schema without dropping tables
	 */
	public void updateSchema()
	{
		new SchemaUpdate(configuration).execute(true, true);
	}
	
	/**
	 * Prints schema update script to console
	 */
	public void printSchemaUpdateScript()
	{
		new SchemaUpdate(configuration).execute(true, false);
	}

	/**
	 * Drops and recreates all hibernate tables
	 */
	public void format()
	{
		try
		{
			final SessionFactory sessionFactory = configuration.buildSessionFactory();
			final Dialect dialect = ((SessionFactoryImpl)sessionFactory).getDialect();
			final String[] drops = configuration.generateDropSchemaScript(dialect);
			final String[] creates = configuration.generateSchemaCreationScript(dialect);
			final Session session = sessionFactory.openSession();
			try
			{
				final Connection connection = session.connection();
				final Statement statement = connection.createStatement();
				executeStatements(connection, statement, splitAlterTables(drops, true));
				executeStatements(connection, statement, splitAlterTables(drops, false));
				executeStatements(connection, statement, creates);
			}
			finally
			{
				session.close();
			}
		}
		catch (HibernateException e)
		{
			throw new WicketRuntimeException(e);
		}
		catch (SQLException e)
		{
			throw new WicketRuntimeException(e);
		}
	}

	/**
	 * @see wicket.contrib.database.Database#newDatabaseSession()
	 */
	public DatabaseSession newDatabaseSession()
	{
		return new HibernateDatabaseSession(this, hibernateSessionFactory
				.openSession(new EmptyInterceptor()
				{
					private static final long serialVersionUID = 1L;

					/**
					 * @see org.hibernate.EmptyInterceptor#onFlushDirty(java.lang.Object,
					 *      java.io.Serializable, java.lang.Object[],
					 *      java.lang.Object[], java.lang.String[],
					 *      org.hibernate.type.Type[])
					 */
					public boolean onFlushDirty(Object dirtyObject, Serializable arg1,
							Object[] arg2, Object[] arg3, String[] arg4, Type[] arg5)
					{
						final IDatabaseObject object = (IDatabaseObject)dirtyObject;
						if (object.getId() == null)
						{
							object.onSave();
						}
						else
						{
							object.onUpdate();
						}
						return super.onFlushDirty(dirtyObject, arg1, arg2, arg3, arg4, arg5);
					}
				}));
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

	/**
	 * Filter statements on start of statement.
	 * 
	 * @param drops
	 *            Statements
	 * @param includeAlterFlag
	 *            If true, everything that starts with alter, else the inverse
	 * @return part of the input
	 */
	private String[] splitAlterTables(String[] drops, boolean includeAlterFlag)
	{
		List temp = new ArrayList();
		for (int i = 0; i < drops.length; i++)
		{
			if (drops[i].toLowerCase().trim().startsWith("alter") == includeAlterFlag)
			{
				temp.add(drops[i]);
			}
		}
		return (String[])temp.toArray(new String[temp.size()]);
	}
}
