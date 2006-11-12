package wicket.contrib.dojo.markup.html;

import wicket.ajax.AjaxRequestTarget;

/**
 * Interface for Dojo callback links.
 * 
 * @author vdemay
 */
public interface IDojoLink
{
	/**
	 * Listener method invoked on the ajax request generated when the user
	 * clicks the link
	 * 
	 * @param target
	 *            the request target.
	 */
	void onClick(final AjaxRequestTarget target);
}