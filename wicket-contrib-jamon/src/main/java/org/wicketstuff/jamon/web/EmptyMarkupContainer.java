package org.wicketstuff.jamon.web;

import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * Empty markup container that can be swapped in and out
 * when doing ajax replacements.
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class EmptyMarkupContainer extends WebMarkupContainer {

    /**
     * Construct. Will use the given id as {@link #setMarkupId(String) markupId}
     * and set the {@link #setOutputMarkupId(boolean) outputMarkupId} flag to <code>true</code>.
     * 
     * @param id
     */
    public EmptyMarkupContainer(String id) {
        super(id);
        setMarkupId(id);
        setOutputMarkupId(true);
    }

}
