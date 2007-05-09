package org.wicketstuff.hibernate.validator;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * <p>
 * Form page to be tested
 * </p>
 * 
 * @author miojo
 */
public class HibernateFormValidatorPageTest extends WebPage {

	public HibernateFormValidatorPageTest() {
		Form form = new Form("form", new CompoundPropertyModel(new MyObject()));
		form.add(new TextField("id"));
		form.add(new TextField("name"));
		form.add(new HibernateFormValidator());

		add(form);
	}

}