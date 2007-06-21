package org.wicketstuff.hibernate;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;

public class HibernateAnnotationComponentInstantiaionListenerTest extends TestCase {

	private WicketTester tester;

	public void setUp() {
		tester = new WicketTester();
		tester.getApplication().addComponentInstantiationListener(new HibernateAnnotationComponentInstantiaionListener());
	}

	public void testNothing() {
		
	}
//	public void testNotNullAnnotationUpdatesComponentToBeRequired() {
//		TestPage page = (TestPage) tester.startPage(TestPage.class);
//
//		assertTrue(page.getIdField().isRequired());
//	}
}
