package wicket.benchmark;

import wicket.benchmark.page.CustomerList;
import wicket.protocol.http.WebApplication;

public class BenchmarkApplication extends WebApplication {
    @Override
    protected void init() {
        getPages().setHomePage(CustomerList.class);
        getSettings().setStripWicketTags(true);
    }
}
