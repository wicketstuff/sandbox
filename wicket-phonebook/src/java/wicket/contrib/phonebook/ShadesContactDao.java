/*
 * ShadesContactDao.java
 *
 * Created on September 14, 2006, 9:29 PM
 *
 * Copyright Geoffrey Rummens Hendrey 2006.
 */

package wicket.contrib.phonebook;

import hendrey.orm.DatabaseSession;
import hendrey.orm.ORMDictionary;
import hendrey.orm.ORMapping;
import hendrey.orm.Query;
import hendrey.orm.Record;
import hendrey.orm.RecordSet;
import hendrey.shades.DatabaseSessionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

/**
 *
 * @author ghendrey
 */
public class ShadesContactDao implements ContactDao {
	ORMDictionary dict = ShadesORMDictionary.getInstance();

	ORMapping orm = dict.getORM("CONTACT");

	DatabaseSession dbSess = DatabaseSessionFactory.newSession(dict);

	public javax.sql.DataSource dataSource;

	/** Creates a new instance of ShadesContactDao */
	public ShadesContactDao() {
		dbSess.printSQL(System.out);
	}

	public Contact load(long id) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
			Query q = dict.getQuery("byId");
			dbSess.setParameter("id", new Long(id));
			return (Contact) dbSess.executeQuery(con, q).populateNext(
					new Contact());
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage(), ex);
		} finally {
			try {
				// dbSess.clear();
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

	}

	public Contact save(Contact contact) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
			System.out.println("saving Contact: " + contact);
			Record[] loadedRecords = dbSess.getRecords(contact);
			if (0 != loadedRecords.length) {
				dbSess.update(con, new Object[] { contact });
				System.out.println("updated");
			} else {
				dbSess.insert(con, contact, new ORMapping[] { orm });
				System.out.println("saved");
			}
			return contact;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage(), ex);
		} finally {
			try {
				dbSess.clear();
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void delete(long id) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
			dbSess.delete(con, dbSess.getRecords(load(id)));
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage(), ex);
		} finally {
			try {
				dbSess.clear();
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public Iterator<Contact> find(QueryParam qp, Contact filter) {
		System.out.println("looking for contacts like " + filter);
		Query q = dict.getQuery("byOrderedResemblance");
		ShadesORMDictionary.filterCandidate.resembles(filter, orm
				.getColumnSet("nonKeyFields"));
		dbSess.setParameter("first", new Integer(qp.getFirst()));
		dbSess.setParameter("count", new Integer(qp.getCount()));
		q.clause("ORDER BY").enable(qp.hasSort());
		dbSess.setParameter("order", qp.getSort());
		if (qp.isSortAsc())
			dbSess.setParameter("direction", "ASC");
		else
			dbSess.setParameter("direction", "DESC");
		/*
		 * if(qp.hasSort()){ dbSess.setParameter("order", qp.getSort());
		 * if(qp.isSortAsc())dbSess.setParameter("direction", "ASC"); else
		 * dbSess.setParameter("direction", "DESC"); }else{ q.clause("ORDER
		 * BY").disable(); //dbSess.setParameter("order","");
		 * //dbSess.setParameter("direction", ""); }
		 */
		Connection con = null;
		List<Contact> list = new ArrayList<Contact>();
		try {
			con = dataSource.getConnection();
			dbSess.executeQuery(con, q).populateList(list, Contact.class);
			System.out.println(list);
			return list.iterator();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage(), ex);
		} finally {
			try {
				dbSess.clear();
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public int count(Contact filter) {
		dbSess.clear();
		Query q = dict.getQuery("byResemblance");
		ShadesORMDictionary.filterCandidate.resembles(filter, orm
				.getColumnSet("nonKeyFields"));
		Connection con = null;
		try {
			return dbSess.count(con = dataSource.getConnection(), q);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage(), ex);
		} finally {
			try {
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<String> getUniqueLastNames() {
		Query q = dict.getQuery("selectDistinctLastnameOnly");
		RecordSet rs;
		Connection con = null;
		try {
			rs = dbSess.executeQuery(con = dataSource.getConnection(), q);
			List<String> lastnames = new ArrayList<String>();
			Contact c = new Contact();
			while (rs.next()) {
				rs.populate(c);
				lastnames.add(c.getLastname());
			}
			return lastnames;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage(), ex);
		} finally {
			try {
				dbSess.clear();
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public final void setDataSource(DataSource ds) {
		this.dataSource = ds;
	}

	public final DataSource getDataSource() {
		return this.dataSource;
	}

}
