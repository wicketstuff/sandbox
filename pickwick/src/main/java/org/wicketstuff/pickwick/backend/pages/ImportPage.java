package org.wicketstuff.pickwick.backend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.backend.panel.ImportAppletPanel;

import com.google.inject.Inject;

public class ImportPage extends BaseAdminPage {

	@Inject
	private Settings settings;
	
	ImportAppletPanel importPanel;
	
	public ImportPage(PageParameters params) {
		super(params);
		TextField path = new TextField("path");
		importPanel = new ImportAppletPanel("import", settings.getImageDirectoryRoot()+"");
		path.add(new AjaxEventBehavior("onchange"){

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				ImportAppletPanel newPanel =  new ImportAppletPanel("import", settings.getImageDirectoryRoot() + "/" + getRequest().getParameter("path"));
				importPanel.replaceWith(newPanel);
				importPanel = newPanel;
				target.addComponent(importPanel);
			}
			
			@Override
			protected CharSequence getCallbackScript() {
				return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl(false) + "&path=' + this.value");
			}
			
		});
		add(path);
		add(importPanel);
	}
	
}
