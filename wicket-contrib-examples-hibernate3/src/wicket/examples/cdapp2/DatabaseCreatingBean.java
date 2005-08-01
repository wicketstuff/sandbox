package wicket.examples.cdapp2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;

import wicket.examples.cdapp2.model.Album;
import wicket.examples.cdapp2.model.Category;

public class DatabaseCreatingBean implements InitializingBean {
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void afterPropertiesSet() throws Exception {
		Session session = sessionFactory.openSession();
		loadData(session);
		session.flush();
		session.close();
	}
	
	private void loadData(Session session) {
    	Category rock = new Category("Rock");
    	Category classical = new Category("Classical");
    	Category blues = new Category("Blues");
    	
    	session.save(rock);
    	session.save(classical);
    	session.save(blues);
    	
    	session.save(new Album("Mudvayne", "Lost and Found", "4/12/2005", rock));
    	session.save(new Album("A Perfect Circle", "Emotive", "11/2/2004", rock));
    	session.save(new Album("Radiohead", "Pablo Honey", "4/20/1993", rock));
    	session.save(new Album("Radiohead", "The Bends", "4/4/1995", rock));
    	session.save(new Album("Radiohead", "OK Computer", "7/1/1999", rock));
    	session.save(new Album("Radiohead", "Kid A", "10/3/2000", rock));
    	session.save(new Album("Radiohead", "Amnesiac", "6/5/2001", rock));
    	session.save(new Album("Radiohead", "Hail To The Thief", "6/10/2003", rock));
    	
    	session.save(new Album("Vivaldi, Antonio", "The Four Seasons", "1/1/1723", classical));
    	session.save(new Album("Bach, Johann Sabastian", "Brandenburg Concertos", "1/1/1721", classical));
    	
    	session.save(new Album("Stevie Ray Vaughan", "Texas Flood", "3/23/1999", blues));
    	
    }
}
