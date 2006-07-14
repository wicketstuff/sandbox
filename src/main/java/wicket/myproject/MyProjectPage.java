package wicket.myproject;

import wicket.markup.html.WebPage;

/**
 * Base class for all pages in the QuickStart application. Any page which
 * subclasses this page can get session properties from MyProjectSession via
 * getMyProjectSession().
 */
public abstract class MyProjectPage extends WebPage
{
	/**
	 * Get downcast session object for easy access by subclasses
	 * 
	 * @return The session
	 */
	public MyProjectSession getMyProjectSession()
	{
		return (MyProjectSession)getSession();
	}
}