package wicket.examples.flickr;

import wicket.markup.html.WebMarkupContainer;
import wicket.util.string.Strings;

/**
 * Markup container that is only visible when no flickr key was provided
 * in the servlet init parameters.
 *
 * @author Martijn Dashorst
 */
public class HelpInfo extends WebMarkupContainer {
	/** for serialization. */
	private static final long serialVersionUID = 1L;
	
	public HelpInfo(String id) {
		super(id);
	}
	
	@Override
	public boolean isVisible() {
		return Strings.isEmpty(FlickrDao.FLICKR_KEY);
	}
}
