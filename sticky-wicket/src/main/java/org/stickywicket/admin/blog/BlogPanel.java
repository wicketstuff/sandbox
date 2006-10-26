// $Id: $
package org.stickywicket.admin.blog;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.stickywicket.admin.AdminPanelInfo;
import org.stickywicket.components.PagingNavigator;
import org.stickywicket.jcr.JcrNodeModel;
import org.stickywicket.jcr.JcrUtil;

import wicket.MarkupContainer;
import wicket.ResourceReference;
import wicket.behavior.HeaderContributor;
import wicket.extensions.markup.html.repeater.data.DataView;
import wicket.extensions.markup.html.repeater.data.IDataProvider;
import wicket.extensions.markup.html.repeater.refreshing.Item;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

@AdminPanelInfo ("adminBlogs")
public class BlogPanel extends Panel {

    private static final long serialVersionUID = 1L;
    private static final ResourceReference CSS = new ResourceReference(BlogPanel.class, BlogPanel.class.getSimpleName() + ".css");

    public BlogPanel(MarkupContainer parent, String id) {
        super(parent, id);
        setOutputMarkupId(true);
        add(HeaderContributor.forCss(CSS));
        
        IDataProvider<Node> blogModel = new IDataProvider<Node>() {

            public Iterator iterator(int first, int count) {
                try {
                    NodeIterator i = JcrUtil.getRootNode().getNode("content").getNodes("blog");
                    i.skip(first);
                    return i;
                }
                catch (RepositoryException e) {
                    throw new RuntimeException(e);
                }
            }

            public IModel<Node> model(Node node) {
                IModel model = new JcrNodeModel();
                model.setObject(node);
                return model;
            }

            public int size() {
                try {
                    return (int)JcrUtil.getRootNode().getNode("content").getNodes("blog").getSize();
                }
                catch (RepositoryException e) {
                    throw new RuntimeException(e);
                }
            }
            
            public void detach() {}
                
        };
        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, getLocale());
        DataView dataView = new DataView(this, "entry", blogModel) {

            @Override
            protected void populateItem(final Item item) {
                try {
                    Node node = (Node)item.getModelObject();
                    Date date = node.getProperty("creationDate").getDate().getTime();
                    String title = node.getProperty("title").getString();
                    String body = node.getProperty("body").getString();
                    new Label(item, "date", dateFormat.format(date));
                    new Label(item, "title", title);
                    new Label(item, "body", body.substring(0, Math.min(body.length(), 100)));
                    new Link(item, "deleteLink") {

                        @Override
                        public void onClick() {
                            try {
                                ((Node)item.getModelObject()).remove();
                            }
                            catch (RepositoryException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        
                    };
                }
                catch (RepositoryException e) {
                    throw new RuntimeException(e);
                }
            }
            
        };
        dataView.setItemsPerPage(10);
        new PagingNavigator(this, "paging", dataView);
    }

}
