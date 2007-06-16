package org.wicketstuff.dojo.examples.yahoomap;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class DojoYahooMapDescriptionPanelSample extends Panel {

    public DojoYahooMapDescriptionPanelSample(String id, final String message) {
        super(id);
        
        add(new Label("label", message));
        add(new AjaxLink("ajaxTest") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                target.appendJavascript("alert('" + message + "')");
            }
        });
    }

}
