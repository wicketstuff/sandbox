package wicket.benchmark.wicket13;

import org.apache.wicket.protocol.http.SecondLevelCacheSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.pagestore.DiskPageStore;
import org.apache.wicket.session.ISessionStore;


/**
 * Benchmark application for testing the second level cache.
 */
public class WicketSecondLevelCacheApplication extends WebApplication {
	@Override
	public Class getHomePage() {
		return CustomerList.class;
	}

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
		return new SecondLevelCacheSessionStore(this, new DiskPageStore());
	}
}
