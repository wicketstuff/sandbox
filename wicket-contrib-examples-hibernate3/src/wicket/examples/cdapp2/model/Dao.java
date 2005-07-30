package wicket.examples.cdapp2.model;

import java.util.List;

import org.hibernate.Session;

import static wicket.examples.cdapp2.CdRequestCycle.COMPONENT_DAO;

import wicket.contrib.data.model.OrderedPageableList;
import wicket.contrib.data.model.QueryList;
import wicket.contrib.data.model.hibernate.HibernateQueryList;

/**
 * A data access object that is meant to be created and destroyed on each
 * request. It's probably a better design to make this a singleton, but that's
 * best done with the help of Spring or EJB's and (for now) outside the scope
 * of this application.
 * 
 * @author Phil Kulak
 */
public class Dao {
	private Session session;

	public Dao(final Session session) {
		this.session = session;
	}
	
	public void merge(final Object entity) {
		session.saveOrUpdate(entity);
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
		protected List getItems(int start, int max, String orderBy) {
			return COMPONENT_DAO.getSession()
				.createQuery(listQuery + orderBy)
				.setString("term", term)
				.setFirstResult(start)
				.setMaxResults(max)
				.list();
		}
	
		@Override
		protected int getCount() {
			return (Integer) COMPONENT_DAO.getSession()
				.createQuery(countQuery)
				.setString("term", term)
				.uniqueResult();
		}
	}
}
