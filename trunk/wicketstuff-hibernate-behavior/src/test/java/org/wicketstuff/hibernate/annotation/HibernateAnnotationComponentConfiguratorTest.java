package org.wicketstuff.hibernate.annotation;

import junit.framework.TestCase;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.tester.WicketTester;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

public class HibernateAnnotationComponentConfiguratorTest extends TestCase {
	private WicketTester tester;

	public void setUp() {
		tester = new WicketTester();
	}

	public void testNotNullAnnotationUpdatesComponentToBeRequired() {
		HibernateAnnotationComponentConfigurator configurator = new HibernateAnnotationComponentConfigurator();

		TextField textField = new TextField("id", new PropertyModel(new MyObject(), "id"));
		configurator.configure(textField);

		assertTrue(textField.isRequired());
	}

	public void testConfiguringPropertyModelWithNoAnnotationsDoesNotCauseError() {
		HibernateAnnotationComponentConfigurator configurator = new HibernateAnnotationComponentConfigurator();

		TextField textField = new TextField("id", new PropertyModel(new MyObject(), "description"));
		configurator.configure(textField);

		assertFalse(textField.isRequired());
	}

	public void testAnnotationsFromOgnlPropertyPathCanBeResolved() {
		HibernateAnnotationComponentConfigurator configurator = new HibernateAnnotationComponentConfigurator();

		TextField textField = new TextField("id", new PropertyModel(new MyObject(), "innerObject.name"));
		configurator.configure(textField);

		assertTrue(textField.isRequired());
	}

	public void testComponentListenerConfiguresComponent() {
		tester.getApplication().addComponentOnBeforeRenderListener(new HibernateAnnotationComponentConfigurator());
		TestPage page = (TestPage) tester.startPage(TestPage.class);

		assertTrue(page.getIdField().isRequired());
	}


	public class MyObject {
		@NotNull
		private String id;

		@Length(max=50)
		private String name;
		
		private String description;
		
		private InnerObject innerObject = new InnerObject();
	}
	
	public class InnerObject {
		@NotNull
		private String name;
	}
}
