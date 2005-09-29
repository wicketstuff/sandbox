package wicket.contrib.data.model.hibernate;

import java.io.Serializable;

import org.hibernate.Session;

import wicket.Component;
import wicket.model.IModel;

/**
 * Wraps a hibernate entity in a model using the given session. When then model
 * is detatched, the session is disconected. The typical use case for this
 * is implementing a long transaction that spans several requests. At the end
 * of the transaction, call {@link #close()} to merge the entity, flush, and
 * close the session.
 * 
 * @author Phil Kulak
 */
public class HibernateLongTransactionModel implements IModel
{
	private Serializable entity;
	private Session session;
	
	/**
	 * Constructor
	 * 
	 * @param id the id of the entity to wrap
	 * @param c the class of the entity to wrap
	 * @param longSession a NEW session that will be managed solely by this model
	 */
	public HibernateLongTransactionModel(Serializable id, Class c, Session longSession) {
		this.session = longSession;
		
		if (!Serializable.class.isAssignableFrom(c))
		{
			throw new IllegalArgumentException("The model to wrap must be serializable.");
		}
		
		entity = (Serializable) longSession.load(c, id);
	}

	public IModel getNestedModel()
	{
		return null;
	}

	public Object getObject(Component component)
	{
		if (session != null && !session.isConnected())
		{
			session.reconnect();
		}
		return entity;
	}

	public void setObject(Component component, Object object)
	{
		throw new UnsupportedOperationException();
	}

	public void detach()
	{
		if (session != null && session.isConnected())
		{
			session.disconnect();
		}
	}
	
	/**
	 * Merges the entity, flushes and closes the session. After this call, the
	 * entity is no longer available.
	 */
	public void close() {
		session.merge(entity);
		session.flush();
		session.close();
		session = null;
		entity = null;
	}
}
