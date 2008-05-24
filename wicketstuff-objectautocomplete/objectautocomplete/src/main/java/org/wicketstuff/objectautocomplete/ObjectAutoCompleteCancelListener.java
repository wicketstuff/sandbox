package org.wicketstuff.objectautocomplete;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Internal listener interface used for the behaviour to callback in case the user cancelled
 * the input via 'escape'
 *
 * @author roland
 * @since May 24, 2008
 */
interface ObjectAutoCompleteCancelListener {

    /**
     * Autocompletion searched has been cancelled on request of the user (i.e. by pressing 'escape')
     *
     * @param pTarget target to add components to add to for updating
     */
    void searchCanceled(AjaxRequestTarget pTarget);
}
