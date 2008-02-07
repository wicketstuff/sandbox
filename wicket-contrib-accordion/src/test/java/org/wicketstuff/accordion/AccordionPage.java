package org.wicketstuff.accordion;

import java.util.ArrayList;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;

public class AccordionPage extends WebPage {
	public AccordionPage() {
		AccordionPanel accordionPanel = new AccordionPanel("accordionMenu");

		ArrayList<WebMarkupContainer> markupItems = new ArrayList<WebMarkupContainer>();

		markupItems.add(new content(AccordionPanelItem.ITEM_ID,
				"Give it some content!"));

		AccordionPanelItem accordionPanelItem = new AccordionPanelItem(
				"MenuItem1", markupItems);

		accordionPanel.addMenu(accordionPanelItem);

		markupItems = new ArrayList<WebMarkupContainer>();

		markupItems.add(new content(AccordionPanelItem.ITEM_ID,
				"Give it some content!"));

		accordionPanelItem = new AccordionPanelItem("MenuItem2", markupItems);

		accordionPanel.addMenu(accordionPanelItem);

		add(accordionPanel);

	}
}
