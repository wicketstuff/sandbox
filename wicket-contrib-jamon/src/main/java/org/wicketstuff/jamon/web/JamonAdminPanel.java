package org.wicketstuff.jamon.web;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * Panel containing the {@link JamonAdminForm}.
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class JamonAdminPanel extends Panel {
    
    public JamonAdminPanel(String id) {
        super(id);
        add(new JamonAdminForm("adminForm"));
    }

}
