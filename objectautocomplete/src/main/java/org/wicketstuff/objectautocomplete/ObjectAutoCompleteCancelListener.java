package org.wicketstuff.objectautocomplete;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author roland
 * @since May 24, 2008
 */
public interface ObjectAutoCompleteCancelListener {
    
    void searchCanceled(AjaxRequestTarget target);
}
