package wicket.benchmark.wicket12;


import wicket.protocol.http.WebApplication;

/**
 * Benchmark application for testing using the HTTP Session store (wicket 1.2
 * mode).
 */
public class WicketHttpSessionStoreApplication extends WebApplication {
	@Override
	protected void init() {
		getMarkupSettings().setStripWicketTags(true);
	}

	@Override
	public Class getHomePage() {
		return CustomerList.class;
	}
}
