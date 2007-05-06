package wicket.examples.flickr;

import wicket.markup.html.WebPage;
import wicket.markup.html.link.BookmarkablePageLink;

public class Index extends WebPage {
	/** Used for serialization. */
	private static final long serialVersionUID = 1L;

	public Index() {
		// show a warning that the flickr api key wasn't set in the web.xml file.
		add(new HelpInfo("noKey"));

		add(new BookmarkablePageLink("standard", wicket.examples.flickr.standard.Index.class));
		add(new BookmarkablePageLink("ajax", wicket.examples.flickr.ajax.Index.class));
	}
}
