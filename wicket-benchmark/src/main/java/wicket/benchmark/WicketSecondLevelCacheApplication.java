package wicket.benchmark;

import wicket.benchmark.wicket.CustomerList;
import wicket.protocol.http.FilePageStore;
import wicket.protocol.http.SecondLevelCacheSessionStore;
import wicket.protocol.http.WebApplication;
import wicket.session.ISessionStore;

/**
 * Benchmark application for testing the second level cache.
 */
public class WicketSecondLevelCacheApplication extends WebApplication {
	@Override
	protected void init() {
		getMarkupSettings().setStripWicketTags(true);
		getRequestLoggerSettings().setRequestLoggerEnabled(false);
	}

	/**
	 * Overrides super explicitly to ensure the SecondLevelCacheSessionStore is
	 * used.
	 */
	protected ISessionStore newSessionStore() {
		return new SecondLevelCacheSessionStore(new FilePageStore());
	}

	@Override
	public Class getHomePage() {
		return CustomerList.class;
	}
}
