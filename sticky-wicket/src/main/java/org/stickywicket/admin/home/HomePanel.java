// $Id: $
package org.stickywicket.admin.home;

import java.text.DateFormat;
import java.util.Date;

import javax.jcr.RepositoryException;

import org.stickywicket.admin.AdminPanelInfo;
import org.stickywicket.jcr.JcrUtil;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.panel.Panel;

@AdminPanelInfo ("adminHome")
public class HomePanel extends Panel {
    
    private static final long serialVersionUID = 1L;
    
    public HomePanel(MarkupContainer parent, String id) throws RepositoryException {
        super(parent, id);
        
        DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, getRequest().getLocale());
        String lastLogIn = dateFormatter.format(new Date());
        String blogEntries = String.valueOf(JcrUtil.getRootNode().getNode("content").getNodes("blog").getSize());
        String comments = String.valueOf(JcrUtil.getRootNode().getNode("content").getNodes("comment").getSize());
        
        new Label(this, "statsLastLogIn", lastLogIn);
        new Label(this, "statsBlogEntries", blogEntries);
        new Label(this, "statsComments", comments);
        new Label(this, "statsGallery", "8126 (in 5 albums)");
    }

}
