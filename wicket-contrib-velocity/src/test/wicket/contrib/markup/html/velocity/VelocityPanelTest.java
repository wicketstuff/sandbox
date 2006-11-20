package wicket.contrib.markup.html.velocity;

import junit.framework.TestCase;

import org.apache.velocity.app.Velocity;

import wicket.util.tester.WicketTester;

/**
 * Tests for <code>VelocityPanel</code>
 * 
 * @see wicket.contrib.markup.html.velocity.VelocityPanel
 */
public class VelocityPanelTest extends TestCase
{
	static {
		try
		{
			Velocity.init();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Could not initialize the Velocity engine", e);
		}
	}

	/**
	 * Basic test
	 */
	public void testVelocityPanel() {
		WicketTester tester = new WicketTester();
		tester.startPage(VelocityPage.class);
		tester.assertContains(VelocityPage.TEST_STRING);
		tester.dumpPage();
	}

	/**
	 * Test with Wicket markup parsing
	 */
	public void testVelocityPanelWithMarkupParsing() {
		WicketTester tester = new WicketTester();
		tester.startPage(VelocityWithMarkupParsingPage.class);
		tester.assertLabel("velocityPanel:message", VelocityPage.TEST_STRING);
		tester.dumpPage();
	}
}
