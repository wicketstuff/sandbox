package org.wicketstuff.jamon.webapp;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.wicketstuff.jamon.JamonAdminPage;

/**
 * Dummy HomePage used by the {@link JamonWebApplication} in tests.
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class HomePage extends WebPage {
    public HomePage() {
        add(new Link("toStatisticsPage") {

            @Override
            public void onClick() {
                setResponsePage(new JamonAdminPage(100));
            }
        });
        add(new Link("toAjaxPage") {
            
            @Override
            public void onClick() {
                setResponsePage(new AjaxPage());
            }
        });
    }
}
