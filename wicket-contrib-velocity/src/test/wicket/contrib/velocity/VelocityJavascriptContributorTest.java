package wicket.contrib.velocity;

import junit.framework.TestCase;
import wicket.util.tester.WicketTester;

/**
 * Test header contributions.
 */
public class VelocityJavascriptContributorTest extends TestCase
{
	/**
	 * Test that the header contribution is added correctly.
	 */
	public void testRenderHead()
	{
		WicketTester tester = new WicketTester();
		tester.startPage(VelocityJavascriptPage.class);
		tester.assertContains("msg1: " + VelocityJavascriptPage.MSG1);
		tester.dumpPage();
	}
}
