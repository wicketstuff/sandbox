package org.apache.wicket;

import org.wicketstuff.iolite.pages.HomePage;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends BaseTest {

	public void testRenderMyPage() {
		// start and render the test page
		wicketTester.startPage(HomePage.class);

		// assert rendered page class
		wicketTester.assertRenderedPage(HomePage.class);

		// assert rendered label component
		wicketTester
				.assertLabel("message",
						"If you see this message wicket is properly configured and running");
	}
}
