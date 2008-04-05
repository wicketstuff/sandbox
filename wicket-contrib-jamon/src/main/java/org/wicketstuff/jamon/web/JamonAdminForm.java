package org.wicketstuff.jamon.web;

import static org.wicketstuff.jamon.web.JamonAdminPage.PATH_TO_MONITOR_DETAILS;
import static org.wicketstuff.jamon.web.JamonAdminPage.PATH_TO_STATISTICS_TABLE;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.wicketstuff.jamon.monitor.AlwaysSatisfiedMonitorSpecification;
import org.wicketstuff.jamon.monitor.JamonRepository;
import org.wicketstuff.jamon.monitor.MonitorLabelPrefixSpecification;
import org.wicketstuff.jamon.monitor.MonitorSpecification;

/**
 * Form for filtering the {@link JamonMonitorTable}.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class JamonAdminForm extends Form {
    
    private static final String ID_OF_RESET_BUTTON = "reset";
    private static final String ID_OF_MONITOR_LABEL = "monitorLabel";
    
    public JamonAdminForm(String id) {
        super(id);
        final TextField monitorLabel = new TextField(ID_OF_MONITOR_LABEL, new Model());
        monitorLabel.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                replaceJamonMonitorTable(monitorLabel, target, new MonitorLabelPrefixSpecification(monitorLabel.getValue()));
            }
        });
        add(monitorLabel);
        add(new AjaxButton(ID_OF_RESET_BUTTON) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                JamonRepository.clear();
                replaceJamonMonitorTable(monitorLabel, target, new AlwaysSatisfiedMonitorSpecification());
                replaceMonitorDetailsPanel(target);
            }

        });
    }
    private void replaceJamonMonitorTable(final TextField monitorLabel, AjaxRequestTarget target, MonitorSpecification specification) {
        JamonMonitorTable toBeReplaced = (JamonMonitorTable) target.getPage().get(PATH_TO_STATISTICS_TABLE);
        JamonMonitorTable replacement = new JamonMonitorTable(PATH_TO_STATISTICS_TABLE, specification, toBeReplaced.getRowsPerPage());
        toBeReplaced.replaceWith(replacement);
        target.addComponent(replacement);
    }

    private void replaceMonitorDetailsPanel(AjaxRequestTarget target) {
        EmptyMarkupContainer emptyMarkupContainer = new EmptyMarkupContainer(PATH_TO_MONITOR_DETAILS);
        target.getPage().get(PATH_TO_MONITOR_DETAILS).replaceWith(emptyMarkupContainer);
        target.addComponent(emptyMarkupContainer);
    }
}
