// $Id: $
package org.stickywicket.admin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.stickywicket.admin.blog.BlogPanel;
import org.stickywicket.admin.create.CreatePanel;
import org.stickywicket.admin.home.HomePanel;

import wicket.MarkupContainer;
import wicket.behavior.HeaderContributor;
import wicket.extensions.markup.html.tabs.AbstractTab;
import wicket.extensions.markup.html.tabs.ITab;
import wicket.extensions.markup.html.tabs.TabbedPanel;
import wicket.markup.html.WebPage;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;

public class AdminPage extends WebPage {

    private static final long serialVersionUID = 1L;
    
    public AdminPage() {
        add(HeaderContributor.forCss(AdminPage.class, "AdminPage.css"));
        
        List<ITab> tabs = new ArrayList<ITab>();
        tabs.add(new Tab(HomePanel.class));
        tabs.add(new Tab(BlogPanel.class));
        tabs.add(new Tab(CreatePanel.class));
        new TabbedPanel(this, "tabs", tabs);
    }
    
    private class Tab extends AbstractTab {
        private static final long serialVersionUID = 1L;
        Class<? extends Panel> clazz;
        public Tab(Class<? extends Panel> clazz) {
            super(new Model<String>(getString(clazz.getAnnotation(AdminPanelInfo.class).value())));
            this.clazz = clazz;
        }
        
        @Override
        public Panel getPanel(MarkupContainer parent, String panelId) {
            try {
                Constructor<? extends Panel> c = clazz.getConstructor(new Class[] { MarkupContainer.class, String.class });
                return c.newInstance(new Object[] { parent, panelId });
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
