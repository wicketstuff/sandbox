package wicket.examples.cdapp;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import wicket.contrib.data.model.hibernate.HibernateDaoSupport;
import org.apache.wicket.examples.cdapp.model.Dao;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.protocol.http.WebSession;

public class CdRequestCycle extends WebRequestCycle {
	private Dao dao;
	
    /** the Hibernate session factory. */
	private final SessionFactory sessionFactory;

	/** the current hibernate session. */
	private Session session = null;
    
    /** our Hibernate data access object for components */
    public static final CdComponentDao COMPONENT_DAO = new CdComponentDao();

	/**
	 * Construct.
	 * @param session session object
	 * @param request request object
	 * @param response response object
	 * @param sessionFactory hibernate session factory
	 */
	public CdRequestCycle(WebSession session,
			WebRequest request, Response response, SessionFactory sessionFactory)
	{
		super(session, request, response);
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	protected void onEndRequest() {
		if(session != null) {
			try {
                session.flush();
				session.close();
			} catch (HibernateException e) {
				throw new RuntimeException(e);
			} finally {
				session = null;
			}
		}
		
		dao = null;
	}

	/**
	 * Lazy load the hibernate session for this request.
	 * @return the session
	 */
	public Session getHibernateSession() {
		if(session == null) {
			session = sessionFactory.openSession();
		}
		return session;
	}
	
	/**
	 * Lazy load the dao for this request.
	 */
	public Dao getDao() {
		if (dao == null) {
			dao = new Dao(getHibernateSession());
		}
		return dao;
	}
	
	/**
	 * Our dao to give out to components. Notice that getSession() is public.
	 * The visibility has been increased merely for convenience when we
	 * are using this class ourselves. If we were using a different session
	 * handling strategy, we would want to keep getSession() protected.
	 */
	public static class CdComponentDao extends HibernateDaoSupport {
        @Override
        public Session getSession() {
            return ((CdRequestCycle) RequestCycle.get()).getHibernateSession();
        }
    };
}
