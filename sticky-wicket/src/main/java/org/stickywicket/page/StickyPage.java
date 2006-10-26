// $Id: $
package org.stickywicket.page;

import org.stickywicket.misc.StickyWicketSession;

import wicket.markup.html.WebPage;

public class StickyPage extends WebPage {

    private static final long serialVersionUID = 1L;
    
    @Override
    public StickyWicketSession getSession() {
    	return (StickyWicketSession) super.getSession();
    }

}
