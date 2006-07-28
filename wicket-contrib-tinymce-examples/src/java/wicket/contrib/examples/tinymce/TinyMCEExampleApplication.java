package wicket.contrib.examples.tinymce;

import wicket.Page;
import wicket.contrib.examples.WicketExamplePage;
import wicket.protocol.http.WebApplication;

/**
 * @author Iulian-Corneliu COSTAN
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
