package org.wicketstuff.pickwick.backend.panel;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

public class ImportAppletPanel extends Panel {

	public ImportAppletPanel(String id, final String uploadPath) {
		super(id);
		setOutputMarkupId(true);
		WebMarkupContainer applet;
		
		applet = new WebMarkupContainer("jupload"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("archive",urlFor(new ResourceReference(ImportAppletPanel.class, "jupload.jar")));
			}
		};
		
		add(applet);
		applet.add(new WebMarkupContainer("param"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("value", "/receiver?uploadDir=" + uploadPath + "/");
			}
		});
	}

}
