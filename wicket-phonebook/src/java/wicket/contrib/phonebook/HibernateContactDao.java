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

import java.sql.SQLException;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Extend Spring's {@link HibernateDaoSupport} to implement {@link ContactDao}.
 *
 * @author igor
 */
public class HibernateContactDao extends HibernateDaoSupport implements ContactDao {

	/**
	 * Load a {@link Contact} from the DB, given it's <tt>id</tt> .
	 * @param id The id of the Contact to load.
	 * @return Contact
	 */
	public Contact load(long id) {
		return (Contact) getHibernateTemplate().get(Contact.class, new Long(id));
	}

	/**
	 * Save the contact to the DB
	 * @param contact
	 */
	public void save(Contact contact) {
		getHibernateTemplate().save(contact);
	}

	/**
	 * Delete a {@link Contact} from the DB, given it's <tt>id</tt>.
	 * @param id The id of the Contact to delete.
	 */
	public void delete(long id) {
		getHibernateTemplate().delete(load(id));
	}

	/**
	 * Query the DB, using the supplied query details.
	 * @param qp Query Paramaters to use.
	 * @return The results of the query as an Iterator.
	 */
	public Iterator find(final QueryParam qp) {
		return (Iterator)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer hql=new StringBuffer("from Contact c ");
				if (qp.hasSort()) {
					hql.append("order by c.")
						.append(qp.getSort())
						.append((qp.isSortAsc())?" asc":" desc");
				}
				return session.createQuery(hql.toString())
					.setFirstResult(qp.getFirst())
					.setMaxResults(qp.getCount())
					.iterate();
			}
		});
	}

	/**
	 * Return the number of Contacts in the DB.
	 * @return count
	 */
	public int count() {
		Integer count=(Integer)
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return (Integer)
						session.createQuery("select count(*) from Contact")
							.uniqueResult();
				}
				
			});
		return count.intValue();
	}

}
