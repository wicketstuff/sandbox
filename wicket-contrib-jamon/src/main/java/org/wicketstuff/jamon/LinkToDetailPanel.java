package org.wicketstuff.jamon;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Panel that serves as a wrapper for the link that is generated to show
 * the details of a Monitor. This is needed due to the way {@link DefaultDataTable}
 * works.
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class LinkToDetailPanel extends Panel {

    /*
     * The actual link to show the details.
     */
    private static final class LinkToDetailLink extends AjaxFallbackLink {
        private final String monitorLabel;
        private LinkToDetailLink(String id, IModel modelForLink) {
            super(id);
            add(new Label("linkText", modelForLink));
            monitorLabel = modelForLink.getObject().toString();
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            MonitorDetailsPanel panel = (MonitorDetailsPanel) target.getPage().get("monitorDetails");
            panel.refresh(monitorLabel);
            target.addComponent(panel, "monitorDetails");
        }
    }

    /**
     * Construct.
     * @param id The id.
     * @param modelForLink The model for the link that is wrapped in this {@link Panel}.
     */
    public LinkToDetailPanel(String id, IModel modelForLink) {
        super(id);
        add(new LinkToDetailLink("linkToDetail", modelForLink));
    }

}
