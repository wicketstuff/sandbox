package org.wicketstuff.hibernate.behavior;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;

public class HibernateAnnotationBehaviorTest extends TestCase {
	private WicketTester tester;

	public void setUp() {
		tester = new WicketTester();
	}
	
	public void testNotNullAnnotationUpdatesComponentToBeRequired() {
		TestPage page = (TestPage) tester.startPage(TestPage.class);

		assertTrue(page.getIdField().isRequired());
	}

//	public void testLengthAnnotationAddsMaxLengthAttributeToComponent() {
//		tester.startPage(TestPage.class);
//
//		tester.assertContains("maxlength=\"50\"");
//	}
}
