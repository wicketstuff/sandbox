// $Id: $
package org.stickywicket.components;

import wicket.MarkupContainer;
import wicket.markup.html.navigation.paging.IPageable;

/**
 * Paging Navigator that replaces the standard Wicket component's markup.
 * 
 * @author almaw
 */
public class PagingNavigator extends wicket.markup.html.navigation.paging.PagingNavigator {

    private static final long serialVersionUID = 1L;

    public PagingNavigator(MarkupContainer parent, String id, IPageable pageable) {
        super(parent, id, pageable);
    }

}
