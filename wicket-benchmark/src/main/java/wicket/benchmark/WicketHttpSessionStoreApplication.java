package wicket.benchmark;

import wicket.benchmark.wicket.CustomerList;
import wicket.protocol.http.HttpSessionStore;
import wicket.protocol.http.WebApplication;
import wicket.session.ISessionStore;

/**
 * Benchmark application for testing using the HTTP Session store (wicket 1.2
 * mode).
 */
public class WicketHttpSessionStoreApplication extends WebApplication {
	@Override
	protected void init() {
		getMarkupSettings().setStripWicketTags(true);
		getRequestLoggerSettings().setRequestLoggerEnabled(false);
	}

	protected ISessionStore newSessionStore() {
		return new HttpSessionStore();
	}

	@Override
	public Class getHomePage() {
		return CustomerList.class;
	}
}
