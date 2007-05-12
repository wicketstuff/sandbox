package org.wicketstuff.hibernate.behavior;

import junit.framework.TestCase;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.tester.WicketTester;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

public class HibernateAnnotationBehaviorTest extends TestCase {

	public void setUp() {
		new WicketTester();
	}
	
	public void testNotNullAnnotationUpdatesComponentToBeRequired() {
		TextField component = new TextField("test", new PropertyModel(new MyObject(), "id"));
		new HibernateAnnotationBehavior().bind(component);
		
		assertTrue(component.isRequired());
	}

	public void testLengthAnnotationAddsMaxLengthAttributeToComponent() {
		TextField component = new TextField("test", new PropertyModel(new MyObject(), "name"));
		new HibernateAnnotationBehavior().bind(component);

		//TODO: verify maxlength attribute is added to component
	}

	public class MyObject {
		@NotNull
		private String id;
		
		@Length(max=50)
		private String name;
	}
}
