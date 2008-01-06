package wicket.benchmark;

import wicket.benchmark.wicket13.CustomerList;

import org.apache.wicket.protocol.http.WebApplication;

public class BenchmarkApplication extends WebApplication {
	@Override
	protected void init() {
		getMarkupSettings().setStripWicketTags(true);
	}

	@Override
	public Class getHomePage() {
		return CustomerList.class;
	}
}
