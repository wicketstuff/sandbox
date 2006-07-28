package wicket.contrib.examples.tinymce;

import wicket.Page;
import wicket.protocol.http.WebApplication;

/**
 * @author Iulian Costan (iulian.costan@gmail.com)
 */
public class TinyMCEExampleApplication extends WebApplication
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see wicket.Application#getHomePage()
	 */
	public Class< ? extends Page> getHomePage()
	{
		return SimpleTinyMCEPage.class;
	}

}
