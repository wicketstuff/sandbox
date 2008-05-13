package persistence;

import java.util.List;

import persistence.provider.GeneralDao;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.jpa.AbstractJpaTests;

/**
 * Base class for simplifying tests.
 * 
 * Inherits from AbstractJpaTests, which means each test is run in a transaction
 * that is rolled back after each test. No need to clean up after yourself.
 * 
 */
public abstract class TestSupport extends AbstractJpaTests {

	protected GeneralDao generalDao;

	@Override
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
	}

	@Required
	public void setDbProvider(GeneralDao dbProvider) {
		this.generalDao = dbProvider;
	}

	protected TestSupport() {
		super();
		setDependencyCheck(false); // To make inherited properties (like
									// DataSource) not required.
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:applicationContext.xml", };
	}

	/**
	 * Assert that an object that matches what you give as parameter has
	 * previously been persisted to Hibernate.
	 * 
	 * @param object
	 */
	public void assertPersistent(Object object) {
		final Session session = getHibernateSession();
		final List list = session.createCriteria(object.getClass()).add(
				Example.create(object)).list();
		assertTrue("Object not in database : " + object, list.size() > 0);
	}

	/**
	 * Extract the Hibernate session from the Entity Manager. Of course, this
	 * requires that we use Hibernate's implementation of JPA EntityManager.
	 * 
	 * @return
	 */
	protected Session getHibernateSession() {
		final Object delegate = sharedEntityManager.getDelegate();
		if (delegate == null)
			throw new IllegalStateException(
					"Oops! The EntityManager\'s delegate is null.");
		if (!(delegate instanceof Session))
			throw new IllegalStateException(
					"Oops! We are apparently not using Hibernate as EntityManager. The delegate is a "
							+ delegate.getClass());
		final Session session = (Session) delegate;
		if (!session.isOpen() && sharedEntityManager.isOpen())
			throw new IllegalStateException(
					"Oops! entityManager.isOpen() but !session.isOpen()");
		return session;
	}

}
