package org.wicketstuff.mergedresources.examples.components;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

public class MyForm extends Form {

	private static final long serialVersionUID = 1L;

	public MyForm(String id) {
		super(id);
		add(HeaderContributor.forCss(MyForm.class, MyForm.class.getSimpleName() + ".css"));
		add(HeaderContributor.forJavaScript(MyForm.class, MyForm.class.getSimpleName() + ".js"));
		add(new AttributeAppender("onsubmit", true, new Model("return validateMyForm()"), ";"));
	}

}
