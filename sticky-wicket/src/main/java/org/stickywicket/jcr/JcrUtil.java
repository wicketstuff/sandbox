// $Id: $
package org.stickywicket.jcr;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.stickywicket.misc.StickyWicketSession;

public final class JcrUtil {
    private JcrUtil() {}
    
    private static final String PREFIX = "";
    
    public static Node getOrCreateNode(Node parent, String child) {
        try {
            return parent.getNode(PREFIX + child);
        }
        catch (PathNotFoundException e) {
            try {
                return parent.addNode(PREFIX + child);
            }
            catch (RepositoryException ee) {
                throw new RuntimeException(ee);
            }
        }
        catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    public static Node addNode(Node parent, String child) {
        try {
            return parent.addNode(PREFIX + child);
        }
        catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    public static Node getChildNode(Node parent, String child) {
        try {
            return parent.getNode(PREFIX + child);
        }
        catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Node getRootNode() {
        try {
            return StickyWicketSession.get().getJcrSession().getRootNode();
        }
        catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}
