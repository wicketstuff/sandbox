package wicket.contrib.dojo.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.ISessionFactory;
import wicket.Session;
import wicket.protocol.http.WebApplication;
import wicket.util.time.Duration;

/**
 * Runs the ExampleApplication when invoked from command line.
 */
public class ExampleApplication extends WebApplication
{    
	/** Logging */
	private static final Log log = LogFactory.getLog(ExampleApplication.class);

    /**
     * Constructor
     */
	public ExampleApplication()
	{
		getPages().setHomePage(Index.class);
		if (!Boolean.getBoolean("cache-markup"))
		{
			getSettings().setResourcePollFrequency(Duration.ONE_SECOND);
			log.info("Markup caching is INACTIVE");
		}
		else
		{
			log.info("Markup caching is ACTIVE");
		}
	}

    /**
     * @see wicket.protocol.http.WebApplication#getSessionFactory()
     */
    public ISessionFactory getSessionFactory()
    {
        return new ISessionFactory()
        {        	
			public Session newSession()
            {
                return new ExampleSession(ExampleApplication.this);
            }
        };
    }
}