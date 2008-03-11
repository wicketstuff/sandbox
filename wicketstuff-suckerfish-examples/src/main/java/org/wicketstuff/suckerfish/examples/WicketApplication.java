package org.wicketstuff.suckerfish.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    
    /**
     * Constructor
     */
	public WicketApplication()
	{
	}
	
	/**
	 * @see wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected void init()
	{
		super.init();
		mountBookmarkablePage("/home", HomePage.class);
		mountBookmarkablePage("/first", MyFirstPage.class);
		mountBookmarkablePage("/second", MySecondPage.class);
	}
}
