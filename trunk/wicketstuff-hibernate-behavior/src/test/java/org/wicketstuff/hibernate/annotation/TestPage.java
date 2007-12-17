package org.wicketstuff.hibernate.annotation;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

public class TestPage extends WebPage {
	
	private TextField idField;

	public TestPage() {
		idField = new TextField("id", new PropertyModel(new MyObject(), "id"));

		add(idField);
	}

	public TextField getIdField() {
		return idField;
	}


	public class MyObject {
		@NotNull
		private String id;
		
		@Length(max=50)
		private String name;
	}
}
