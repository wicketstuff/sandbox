package wicket.examples.cdapp2;

import org.springframework.context.ApplicationContext;

import wicket.Response;
import wicket.contrib.spring.SpringContextLocator;
import wicket.examples.cdapp2.model.Dao;
import wicket.protocol.http.WebRequest;
import wicket.protocol.http.WebRequestCycle;
import wicket.protocol.http.WebSession;

public class CdRequestCycle extends WebRequestCycle {
	private Dao dao;
    
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
			WebRequest request, Response response)
	{
		super(session, request, response);
	}
	
	public Dao getDao() {
		return (Dao) getBean("dao");
	}
	
	public Object getBean(String bean) {
		return getContext().getBean(bean);
	}
	
	private ApplicationContext getContext() {
		return SpringContextLocator.getApplicationContext(this);
	}
}
