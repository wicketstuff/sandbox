package org.wicketstuff.hibernate.validator;

import java.util.HashMap;

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

	WicketTester tester;

	public void setUp() {
		tester = new WicketTester();
		tester.startPage(HibernateFormValidatorPageTest.class);
	}

	public void testNotNullFields() {
		FormTester form = tester.newFormTester("form", true);
		form.submit();

		tester.assertErrorMessages(new String[] { "may not be null" });
	}

	@SuppressWarnings("unchecked")
	public void testFilledNotNullFields() {
		/*
		 * this test fails because parameters are not been set to ModelObject
		 * internally. a bug maybe?
		 */
		FormTester form = tester.newFormTester("form");
		form.setValue("id", "555");
		form.setValue("name", "foo");

		form.submit();

		tester.assertNoErrorMessage();
	}

}