/**
 * 
 */
package org.wicketstuff.extensions;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.IModel;

/**
 * 
 * <p>Created 12.04.2008 23:52:51</p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class ExternalModalWindow extends ModalWindow {

	/**
	 * Create a new ExternalModalWindow
	 */
	public ExternalModalWindow(final String id, IModel/*<String>*/ model) {
		super(id);
		ExternalInlineFrame externalInlineFrame = new ExternalInlineFrame(getContentId(), model);
		setContent(externalInlineFrame);
	}

}
