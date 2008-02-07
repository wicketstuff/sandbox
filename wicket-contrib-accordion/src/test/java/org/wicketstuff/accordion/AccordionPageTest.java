package org.wicketstuff.accordion;

import org.wicketstuff.WicketTestCase;

public class AccordionPageTest extends WicketTestCase {

	public void testCanRender() {

		tester.startPage(new AccordionPage());
		tester.assertRenderedPage(AccordionPage.class);

	}

}
