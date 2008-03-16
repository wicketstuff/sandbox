package org.wicketstuff.jamon.webapp;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
/**
 * Dummy page that provides an AjaxLink that will update some
 * Component on the Page.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class AjaxPage extends WebPage {
    int x = 0;
    private Label label;
    public AjaxPage() {
        add(new AjaxLink("ajaxLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                label.setModelObject("after"+ x++);
                target.addComponent(label);
            }
        });
        
        label = new Label("feedbackMessage", "before");
        label.setOutputMarkupId(true);
        add(label);
    }
}
