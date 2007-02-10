package wicket.contrib.examples.tinymce;

import wicket.contrib.examples.WicketExamplePage;
import wicket.markup.html.link.Link;

/**
 * @author Iulian Costan (iulian.costan@gmail.com)
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
			private static final long serialVersionUID = 1L;

			public void onClick()
			{
				setResponsePage(SimpleTinyMCEPage.class);
				setRedirect(true);
			}
		};
		new Link(this, "advanced")
		{
			private static final long serialVersionUID = 1L;

			public void onClick()
			{
				setResponsePage(AdvancedTinyMCEPage.class);
				setRedirect(true);
			}
		};
		new Link(this, "full")
		{
			private static final long serialVersionUID = 1L;

			public void onClick()
			{
				setResponsePage(FullFeaturedTinyMCEPage.class);
				setRedirect(true);
			}
		};
		new Link(this, "word")
		{
			private static final long serialVersionUID = 1L;

			public void onClick()
			{
				setResponsePage(WordTinyMCEPage.class);
				setRedirect(true);
			}
		};
		new Link(this, "exact")
		{
			private static final long serialVersionUID = 1L;
			
			public void onClick()
			{
				setResponsePage(ExactModeTinyMCEPage.class);
				setRedirect(true);
			}
		};
	}
}
