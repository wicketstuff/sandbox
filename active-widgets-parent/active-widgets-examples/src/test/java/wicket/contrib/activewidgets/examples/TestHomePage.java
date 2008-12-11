package wicket.contrib.activewidgets.examples;

import junit.framework.TestCase;
import org.apache.wicket.util.tester.WicketTester;

import wicket.contrib.activewidgets.examples.web.page.WicketExamplePage;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	public void setUp()
	{
		tester = new WicketTester();
	}

	public void testRenderMyPage()
	{
		//start and render the test page
		tester.startPage(WicketExamplePage.class);

		//assert rendered page class
		tester.assertRenderedPage(WicketExamplePage.class);

		//assert rendered label component
		tester.assertLabel("message", "If you see this message wicket is properly configured and running");
	}
}
