package wicket.examples.flickr;

import wicket.markup.html.panel.Panel;
import wicket.util.string.Strings;

/**
 * Markup container that is only visible when no flickr key was provided
 * in the servlet init parameters.
 *
 * @author Martijn Dashorst
 */
public class HelpInfo extends Panel {
	/** for serialization. */
	private static final long serialVersionUID = 1L;
	
	public HelpInfo(String id) {
		super(id);
		setRenderBodyOnly(true);
	}
	
	@Override
	public boolean isVisible() {
		return Strings.isEmpty(FlickrDao.FLICKR_KEY);
	}
}
