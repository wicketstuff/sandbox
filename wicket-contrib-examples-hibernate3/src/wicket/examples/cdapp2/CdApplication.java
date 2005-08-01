package wicket.examples.cdapp2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import wicket.IRequestCycleFactory;
import wicket.ISessionFactory;
import wicket.Request;
import wicket.RequestCycle;
import wicket.Response;
import wicket.Session;
import wicket.examples.cdapp2.page.BrowseAlbums;
import wicket.protocol.http.WebApplication;
import wicket.protocol.http.WebRequest;
import wicket.protocol.http.WebSession;
import wicket.util.convert.Converter;
import wicket.util.convert.IConverter;
import wicket.util.convert.IConverterFactory;
import wicket.util.convert.converters.DateConverter;

public class CdApplication extends WebApplication implements ISessionFactory {
    /**
     * custom request cycle factory.
     */
    private IRequestCycleFactory requestCycleFactory = new IRequestCycleFactory() {
        public RequestCycle newRequestCycle(Session session, Request request, 
                Response response) {
            return new CdRequestCycle((WebSession)session, (WebRequest)request, response);
        }
    };
    
    public CdApplication() {
        super();

        // There can be a LOT of wicket tags in a RAD panel.
        getSettings().setStripWicketTags(true);
        getPages().setHomePage(BrowseAlbums.class);
        setSessionFactory(this);
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
}
