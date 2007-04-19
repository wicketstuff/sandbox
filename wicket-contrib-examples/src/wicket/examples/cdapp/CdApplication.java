package wicket.examples.cdapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import org.apache.wicket.IRequestCycleFactory;
import org.apache.wicket.ISessionFactory;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.examples.cdapp.model.Album;
import org.apache.wicket.examples.cdapp.model.Category;
import org.apache.wicket.examples.cdapp.page.BrowseAlbums;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.util.convert.Converter;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.IConverterFactory;
import org.apache.wicket.util.convert.converters.DateConverter;

public class CdApplication extends WebApplication implements ISessionFactory {
    private SessionFactory sessionFactory;
    
    /**
     * custom request cycle factory.
     */
    private IRequestCycleFactory requestCycleFactory = new IRequestCycleFactory() {
        public RequestCycle newRequestCycle(Session session, Request request, 
                Response response) {
            return new CdRequestCycle((WebSession)session, 
                    (WebRequest)request, response, sessionFactory);
        }
    };
    
    public CdApplication() {
        super();
        AnnotationConfiguration config = new AnnotationConfiguration();
        config.configure();
        sessionFactory = config.buildSessionFactory();

        getPages().setHomePage(BrowseAlbums.class);
        setSessionFactory(this);
        
        // There can be a LOT of wicket tags in a RAD panel.
        getSettings().setStripWicketTags(true);
        
        org.hibernate.Session session = sessionFactory.openSession();
        loadData(session);
        session.flush();
        session.close();
    }
    
    @Override
	public IConverterFactory getConverterFactory() {
		return new IConverterFactory() {
			public IConverter newConverter(Locale locale) {
				DateConverter dateConverter = new DateConverter();
		        dateConverter.setDateFormat(locale, new SimpleDateFormat("m/d/yyyy"));
		        
		        Converter converter = new Converter(locale);
		        converter.set(Date.class, dateConverter);
		        return converter;
			}
		};
	}

	public org.hibernate.Session getNewSession() {
        return sessionFactory.openSession();
    }
    
    /**
     * @see wicket.ISessionFactory#newSession()
     */
    public Session newSession() {
        return new WebSession(CdApplication.this) {
            @Override
            protected IRequestCycleFactory getRequestCycleFactory() {
                return requestCycleFactory;
            }
        };
    }
    
    private void loadData(org.hibernate.Session session) {
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
