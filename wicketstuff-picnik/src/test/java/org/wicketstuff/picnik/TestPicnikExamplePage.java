package org.wicketstuff.picnik;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class TestPicnikExamplePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester();
	}

	@Test
	public void testRenderMyPage()
	{
		//start and render the test page
		tester.startPage(PicnikExamplePage.class);

		//assert rendered page class
		tester.assertRenderedPage(PicnikExamplePage.class);
	}
}
