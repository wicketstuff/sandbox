package org.apache.wicket;

import org.apache.wicket.util.tester.FormTester;
import org.wicketstuff.iolite.pages.AddMessagePage;
import org.wicketstuff.iolite.persistence.domain.Message;

/**
 * Simple test using the WicketTester
 */
public class TestAddMessagePage extends BaseTest {

	public void testRenderPageAndAddMessage() {
		// start and render the test page
		wicketTester.startPage(AddMessagePage.class);

		// assert rendered page class
		wicketTester.assertRenderedPage(AddMessagePage.class);

		FormTester formTester = wicketTester.newFormTester("form");
		formTester.setValue("message", "hello world");
		formTester.submit();

		assertEquals(1, generalRepository.getAllAsList(Message.class).size());

	}
}
