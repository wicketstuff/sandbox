/**
 * 
 */
package org.wicketstuff.picnik;

import java.util.HashMap;

import org.apache.wicket.Request;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.util.template.TextTemplateHeaderContributor;

/**
 * Page which does nothing but close a modal window in onload.
 * <p>Created 13.04.2008 10:42:17</p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class CloseModalPage extends WebPage {

	/**
	 * Create a new CloseModalPage
	 */
	public CloseModalPage() {
		this("/");
	}

	/**
	 * Create a new CloseModalPage.
	 * Sometimes Picnik in a box may leave the box (e.g. authenticating to Flickr)
	 * In this case, the user leaves your site and ends up back on Picnik.com.
	 * They are then no longer in a frame when they hit close.
	 * In this case, you should redirect to an appropriate page on your site - the fallbackUrl.	
	 * @param fallbackUrl used when modal was exited by now
	 */
	public CloseModalPage(String fallbackUrl) {
		super();
	
		HashMap<String, String> m = new HashMap<String, String>();
		m.put("fallbackUrl", fallbackUrl);
		add(TextTemplateHeaderContributor.forJavaScript(getClass(), "closeModalFromIFrame.js", new Model(m)));		
	}
}
