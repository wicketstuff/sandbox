package wicket.contrib.dojo.widgets;

import wicket.contrib.dojo.WicketTestCase;

public class StylingWebMarkupContainerPageTest extends WicketTestCase {

	public StylingWebMarkupContainerPageTest(String name)
	{
		super(name);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void testskinnedPage() throws Exception
	{
		executeTest(StylingWebMarkupContainerPage.class, "StylingWebMarkupContainerPage_ExpectedResult.html");
	}

}
