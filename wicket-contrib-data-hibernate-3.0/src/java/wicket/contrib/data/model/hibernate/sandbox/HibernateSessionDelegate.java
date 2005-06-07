package wicket.contrib.data.model.hibernate.sandbox;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import wicket.contrib.data.model.hibernate.IHibernateSessionDelegate;

/**
 * A simple implementation of IHibernateSessionDelegate that uses a session
 * factory. This class will call getCurrentSession() on the factory, 
 * so if you are not using a session-per-request pattern, this is no the 
 * way to go. However, if you are using multiple sessions per request, you
 * probably don't want to be using a session delegate at all.
 * 
 * @author Phil Kulak
 */
public class HibernateSessionDelegate implements IHibernateSessionDelegate
{
	private SessionFactory factory;
	
	/**
	 * Wraps a {@link org.hibernate.SessionFactory}.
	 * 
	 * @param factory the factory to wrap
	 */
	public HibernateSessionDelegate(SessionFactory factory)
	{
		this.factory = factory;
	}
	
	/**
	 * @see wicket.contrib.data.model.hibernate.IHibernateSessionDelegate
	 */
	public Session getSession()
	{
		return factory.getCurrentSession();
	}
}
