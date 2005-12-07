/*
 * $Id$
 * $Revision$
 * $Date$
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
package wicket.contrib.phonebook;

import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * implements {@link ContactDao}.
 * 
 * @author igor
 */
public class HibernateContactDao implements ContactDao {

	private SessionFactory factory;

	/**
	 * Setter for session factory. Spring will use this to inject the session
	 * factory into the dao.
	 * 
	 * @param factory
	 *            hibernate session factory
	 */
	public void setSessionFactory(SessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * Helper method for retrieving hibernate session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return factory.getCurrentSession();
	}

	/**
	 * Load a {@link Contact} from the DB, given it's <tt>id</tt> .
	 * 
	 * @param id
	 *            The id of the Contact to load.
	 * @return Contact
	 */
	public Contact load(long id) {
		return (Contact) getSession().get(Contact.class, new Long(id));
	}

	/**
	 * Save the contact to the DB
	 * 
	 * @param contact
	 * @return persistent instance of contact
	 */
	public Contact save(Contact contact) {
		return (Contact) getSession().merge(contact);
	}

	/**
	 * Delete a {@link Contact} from the DB, given it's <tt>id</tt>.
	 * 
	 * @param id
	 *            The id of the Contact to delete.
	 */
	public void delete(long id) {
		getSession().delete(load(id));
	}

	/**
	 * Query the DB, using the supplied query details.
	 * 
	 * @param qp
	 *            Query Paramaters to use.
	 * @return The results of the query as an Iterator.
	 */
	public Iterator find(final QueryParam qp, Contact filter) {
		//TODO filter application here and in the count needs to be clead up/factored out
		StringBuffer hql = new StringBuffer("from Contact c where 1=1 ");
		if (filter.getFirstname()!=null&&filter.getFirstname().trim().length()>0) {
			hql.append(" and upper(c.firstname) like (:fname) ");
		}
		if (filter.getLastname()!=null&&!filter.getLastname().equals("ANY")) {
			hql.append(" and c.lastname=:lname ");
		}
		if (filter.getEmail()!=null&&filter.getEmail().trim().length()>0) {
			hql.append(" and c.email=:email");
		}
		
		if (qp.hasSort()) {
			hql.append("order by upper(c.").append(qp.getSort()).append(") ").append(
					(qp.isSortAsc()) ? " asc" : " desc");
		}
		Query q=getSession().createQuery(hql.toString()).setFirstResult(
				qp.getFirst()).setMaxResults(qp.getCount());
		
		if (filter.getFirstname()!=null&&filter.getFirstname().trim().length()>0) {
			q.setParameter("fname", "%"+filter.getFirstname().trim().toUpperCase()+"%");
		}
		if (filter.getLastname()!=null&&!filter.getLastname().equals("ANY")) {
			q.setParameter("lname", filter.getLastname());
		}
		if (filter.getEmail()!=null&&filter.getEmail().trim().length()>0) {
			q.setParameter("email", filter.getEmail());
		}
		return q.iterate();

	}

	/**
	 * Return the number of Contacts in the DB.
	 * 
	 * @return count
	 */
	public int count(Contact filter) {

		StringBuffer hql=new StringBuffer("select count(*) from Contact c where 1=1 ");
		
		if (filter.getFirstname()!=null&&filter.getFirstname().trim().length()>0) {
			hql.append(" and upper(c.firstname) like (:fname) ");
		}
		if (filter.getLastname()!=null&&!filter.getLastname().equals("ANY")) {
			hql.append(" and c.lastname=:lname ");
		}
		if (filter.getEmail()!=null&&filter.getEmail().trim().length()>0) {
			hql.append(" and c.email=:email");
		}

		
		
		Query q=getSession().createQuery(hql.toString());
		
		if (filter.getFirstname()!=null&&filter.getFirstname().trim().length()>0) {
			q.setParameter("fname", "%"+filter.getFirstname().trim().toUpperCase()+"%");
		}
		if (filter.getLastname()!=null&&!filter.getLastname().equals("ANY")) {
			q.setParameter("lname", filter.getLastname());
		}
		if (filter.getEmail()!=null&&filter.getEmail().trim().length()>0) {
			q.setParameter("email", filter.getEmail());
		}

		return ((Integer)q.uniqueResult()).intValue();
	}


}
