package org.wicketstuff.dojo.examples;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.wicketstuff.dojo.AbstractDefaultDojoBehavior;

public class CustomDojoClass extends WebPage {

	public CustomDojoClass() {
		super();
		this.getApplication().setMetaData(AbstractDefaultDojoBehavior.USE_CUSTOM_DOJO_DIST, new CompressedResourceReference(CustomDojoClass.class, "dojo.js"));
		TextField field = new TextField("text");
		add(field);
		field.add(new AbstractDefaultDojoBehavior(){

			@Override
			protected void respond(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

}
