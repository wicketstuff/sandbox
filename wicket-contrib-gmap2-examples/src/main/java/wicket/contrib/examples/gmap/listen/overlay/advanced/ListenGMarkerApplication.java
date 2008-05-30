package wicket.contrib.examples.gmap.listen.overlay.advanced;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.util.time.Duration;

import wicket.contrib.examples.GMapExampleApplication;

public class ListenGMarkerApplication extends GMapExampleApplication
{

	/**
	 * @see wicket.protocol.http.WebApplication#init()
	 */
	@Override
	protected void init()
	{
		getResourceSettings().setResourcePollFrequency(Duration.seconds(10));
	}

	@Override
	public Class<? extends Page<?>> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected ISessionStore newSessionStore()
	{
		return new HttpSessionStore(this);
	}
}