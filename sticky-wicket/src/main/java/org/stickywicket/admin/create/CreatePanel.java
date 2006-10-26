// $Id: $
package org.stickywicket.admin.create;

import java.util.Calendar;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.stickywicket.admin.AdminPage;
import org.stickywicket.admin.AdminPanelInfo;
import org.stickywicket.jcr.JcrUtil;

import wicket.MarkupContainer;
import wicket.ResourceReference;
import wicket.behavior.HeaderContributor;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.Panel;
import wicket.model.CompoundPropertyModel;

@AdminPanelInfo ("adminCreate")
public class CreatePanel extends Panel {

    private static final long serialVersionUID = 1L;

    private String title, body;
    
    private static final ResourceReference CSS = new ResourceReference(CreatePanel.class, CreatePanel.class.getSimpleName() + ".css");
    
    public CreatePanel(MarkupContainer parent, String id) {
        super(parent, id);
        
        add(HeaderContributor.forCss(CSS));
        
        Form form = new Form(this, "form", new CompoundPropertyModel(this)) {
            @Override
            protected void onSubmit() {
                Node root = JcrUtil.getRootNode();
                Node contentNode = JcrUtil.getOrCreateNode(root, "content");
                Node blogEntryNode = JcrUtil.addNode(contentNode, "blog");
                try {
                    blogEntryNode.setProperty("creationDate", Calendar.getInstance());
                    blogEntryNode.setProperty("title", title);
                    blogEntryNode.setProperty("body", body);
                    root.save();
                    setResponsePage(AdminPage.class);
                }
                catch (RepositoryException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        new TextField(form, "title");
        new TextArea(form, "body");
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
