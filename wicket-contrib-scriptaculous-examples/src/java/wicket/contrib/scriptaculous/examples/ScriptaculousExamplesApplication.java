package wicket.contrib.scriptaculous.examples;

import wicket.protocol.http.WebApplication;

public class ScriptaculousExamplesApplication extends WebApplication {

    protected void init() {
        super.init();
        getSettings().configure("development");
        getSettings().setThrowExceptionOnMissingResource(false);
        getSettings().setAutomaticLinking(true);
        getPages().setHomePage(ScriptaculousExamplesHomePage.class);
    }
}
