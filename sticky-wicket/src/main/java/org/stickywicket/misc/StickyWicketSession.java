// $Id: $
package org.stickywicket.misc;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.stickywicket.jcr.JcrUtil;

import wicket.protocol.http.WebSession;

public class StickyWicketSession extends WebSession {

    private static final long serialVersionUID = 1L;

    private transient Session jcrSession;
    
    public StickyWicketSession(StickyWicketApplication application) {
        super(application);
    }
    
    public Session getJcrSession() {
        if (jcrSession != null) {
            return jcrSession;
        }
        try {
            jcrSession = StickyWicketApplication.get().getRepository().login(new SimpleCredentials("userid", "".toCharArray()));
            JcrUtil.getOrCreateNode(jcrSession.getRootNode(), "content");
        }
        catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
        return jcrSession;
    }

    public static StickyWicketSession get() {
        return (StickyWicketSession)WebSession.get();
    }
}
