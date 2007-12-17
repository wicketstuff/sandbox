package wicket.examples.flickr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.protocol.http.WebApplication;

/**
 * Runs the QuickStartApplication when invoked from command line.
 */
public class FlickrApplication extends WebApplication
{    
	/** Logging */
	private static final Log log = LogFactory.getLog(FlickrApplication.class);

    /**
     * Constructor
     */
	public FlickrApplication()
	{
		configure("development");
	}
	@Override
	protected void init() {
		FlickrDao.FLICKR_KEY = getWicketServlet().getInitParameter("flickr_key");
	}
	@Override
	public Class getHomePage() {
		return Index.class;
	}
}