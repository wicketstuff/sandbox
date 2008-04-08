package org.wicketstuff.jamon.web;

import static org.wicketstuff.jamon.web.JamonAdminPage.PATH_TO_MONITOR_DETAILS;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

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
            add(new AttributeModifier("class", true, new Model("jamonLinkToDetailPanel")));
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            Component componentToBeReplaced = target.getPage().get(PATH_TO_MONITOR_DETAILS);
            JamonMonitorDetailsPanel replacement = new JamonMonitorDetailsPanel(PATH_TO_MONITOR_DETAILS, monitorLabel);
            componentToBeReplaced.replaceWith(replacement);
            target.addComponent(replacement);
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
