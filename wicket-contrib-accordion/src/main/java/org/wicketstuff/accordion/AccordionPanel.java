package org.wicketstuff.accordion;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;

/**
 * 
 * @author Nino Martinez Wael (nino.martinez.wael @ gmail.com)
 * 
 */
public class AccordionPanel extends Panel implements IHeaderContributor {

	protected List<AccordionPanelItem> accordionMenu = new ArrayList<AccordionPanelItem>();

	private ResourceReference JAVASCRIPT = new CompressedResourceReference(
			AccordionPanel.class, "accordion-menu-v2.js");

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public AccordionPanel(String id) {
		super(id);
		add(new ListView("accordionMenu", accordionMenu) {
			@Override
			protected void populateItem(ListItem item) {
				AccordionPanelItem accordionPanelItem = (AccordionPanelItem) item
						.getModelObject();
				item.add(accordionPanelItem);

			}
		});

	}

	protected void addMenu(AccordionPanelItem accordionPanelItem) {
		accordionMenu.add(accordionPanelItem);
	}

	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference(JAVASCRIPT);

	};

}
