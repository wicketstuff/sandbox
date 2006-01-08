package wicket.contrib.scriptaculous.examples;

import wicket.protocol.http.WebApplication;

/**
 * 
 */
public class ScriptaculousExamplesApplication extends WebApplication {

    protected void init() {
        super.init();
        configure("development");
        getResourceSettings().setThrowExceptionOnMissingResource(false);
        getMarkupSettings().setAutomaticLinking(true);
    }
    
    /**
     * @return class
     */
    public Class getHomePage()
    {
    	return ScriptaculousExamplesHomePage.class;
    }
}
