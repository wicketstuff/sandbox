package wicket.examples.cdapp2.model;

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import static wicket.examples.cdapp2.CdRequestCycle.COMPONENT_DAO;

import wicket.contrib.data.model.OrderedPageableList;
import wicket.contrib.data.model.QueryList;
import wicket.contrib.data.model.hibernate.HibernateQueryList;
import wicket.contrib.data.model.hibernate.IHibernateDao.IHibernateCallback;
import wicket.examples.cdapp2.CdComponentDao;

/**
 * Our one and only DAO for this application. This is a Spring-managed bean.
 * 
 * @author Phil Kulak
 */
public class Dao extends HibernateDaoSupport {
	private CdComponentDao componentDao;
	
	@Override
	public void initDao() {
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_EAGER);
	}
	
	public void setComponentDao(CdComponentDao componentDao) {
		this.componentDao = componentDao;
	}

	public void merge(final Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}
	
	public OrderedPageableList findAllCategories() {
		return new HibernateQueryList("FROM Category e", COMPONENT_DAO)
			.addOrder("name");
	}
	
	public OrderedPageableList findAllAlbums() {
		return new HibernateQueryList("FROM Album e", COMPONENT_DAO);
	}
	
	public OrderedPageableList searchAlbums(String term) {
		return new SearchAlbums(term);
	}
	
	private static class SearchAlbums extends QueryList {
		private static final String listQuery =
			"FROM Album e " +
			"WHERE LOWER(e.artist) = LOWER(:term) " +
			"  OR LOWER(e.title) = LOWER(:term) " +
			"  OR LOWER(e.category.name) = LOWER(:term)";
		
		private static final String countQuery = "SELECT COUNT(*) " + listQuery;
		
		private final String term;
		
		public SearchAlbums(String term) {
			this.term = term;
		}
		
		@Override
		protected List getItems(final int start, final int max, final String orderBy) {
			return (List) COMPONENT_DAO.execute(new IHibernateCallback() {
				public Object execute(Session session) {
					return session.createQuery(listQuery + orderBy)
						.setString("term", term)
						.setFirstResult(start)
						.setMaxResults(max)
						.list();
				}
			});
		}
	
		@Override
		protected int getCount() {
			return (Integer) COMPONENT_DAO.execute(new IHibernateCallback() {
				public Object execute(Session session) {
					return session.createQuery(countQuery)
						.setString("term", term)
						.uniqueResult();
				}
			});
		}
	}
}
