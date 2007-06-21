package org.wicketstuff.hibernate.validator;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;

/**
 * <p>
 * HibernateFormValidator TestCase
 * </p>
 * 
 * @author miojo
 */
public class HibernateFormValidatorTest extends TestCase {

	private WicketTester tester;

	protected void setUp() throws Exception {
		tester = new WicketTester();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		tester.destroy();
		super.tearDown();
	}

	public void testNotNullFields() {
		tester.startPage(HibernateFormValidatorPage.class);
		tester.assertRenderedPage(HibernateFormValidatorPage.class);

		FormTester form = tester.newFormTester("form", true);
		form.submit();

		tester.assertErrorMessages(new String[] { "may not be null" });
	}

//	@SuppressWarnings("unchecked")
//	public void testFilledNotNullFields() {
//		tester.startPage(HibernateFormValidatorPage.class);
//		tester.assertRenderedPage(HibernateFormValidatorPage.class);
//
//		FormTester form = tester.newFormTester("form");
//
//		form.setValue("id", "555");
//		form.setValue("name", "foo");
//
//		form.submit();
//
//		tester.assertNoErrorMessage();
//	}
}