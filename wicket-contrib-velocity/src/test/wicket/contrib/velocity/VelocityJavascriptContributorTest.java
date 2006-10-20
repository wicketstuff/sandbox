package wicket.contrib.velocity;

import junit.framework.TestCase;
import wicket.util.tester.WicketTester;

public class VelocityJavascriptContributorTest extends TestCase
{
	public void testRenderHead()
	{
		WicketTester tester = new WicketTester();
		tester.startPage(VelocityJavascriptPage.class);
		tester.assertContains("msg1: " + VelocityJavascriptPage.MSG1);
		tester.dumpPage();
	}
}
