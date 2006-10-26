// $Id: $
package org.stickywicket.jcr;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.stickywicket.misc.StickyWicketSession;

import wicket.model.IModel;

public class JcrNodeModel implements IModel<Node> {

    private static final long serialVersionUID = 1L;
    String path;
    
    public Node getObject() {
        try {
            return (Node)StickyWicketSession.get().getJcrSession().getItem(path);
        }
        catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    public void setObject(Node object) {
        try {
            path = object.getPath();
        }
        catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    public void detach() {}

}
