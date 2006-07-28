package wicket.contrib.examples.tinymce;

import wicket.contrib.examples.WicketExamplePage;
import wicket.markup.html.link.Link;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCEBasePage extends WicketExamplePage
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public TinyMCEBasePage()
	{
		new Link(this, "simple")
		{

			public void onClick()
			{
				setResponsePage(SimpleTinyMCEPage.class);
				setRedirect(true);
			}
		};
		new Link(this, "advanced")
		{

			public void onClick()
			{
				setResponsePage(AdvancedTinyMCEPage.class);
				setRedirect(true);
			}
		};
		new Link(this, "full")
		{

			public void onClick()
			{
				setResponsePage(FullFeaturedTinyMCEPage.class);
				setRedirect(true);
			}
		};
		new Link(this, "word")
		{

			public void onClick()
			{
				setResponsePage(WordTinyMCEPage.class);
				setRedirect(true);
			}
		};
	}
}
