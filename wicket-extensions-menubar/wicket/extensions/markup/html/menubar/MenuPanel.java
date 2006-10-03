/*
 * This piece of code is dedicated to the wicket project (http://www.wicketframework.org).
 */
package wicket.extensions.markup.html.menubar;


import java.util.List;

import wicket.AttributeModifier;
import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.Loop;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;


/**
 * This is a internal class. It is responsible for rendering a visible {@link Menu}.
 *
 * @author Stefan Lindner (lindner@visionet.de)
 */
class MenuPanel extends Panel<String> {

	private static final long serialVersionUID = 1L;

	private List<MenuItem> menuItems;

	protected MenuPanel(MarkupContainer parent, final String id, List<MenuItem> menuItems) {
		super(parent, id);
		if (menuItems == null) {
			throw new IllegalArgumentException("argument [menuItems] cannot be null");
		}
		if (menuItems.size() < 1) {
			throw new IllegalArgumentException(
					"argument [menuItems] must contain a list of at least one menuItem");
		}

		this.menuItems = menuItems;

		// add the loop used to generate menu items
		new Loop(this, "items", menuItems.size()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(LoopItem item) {
				final int index = item.getIteration();
				final MenuItem menuItem = MenuPanel.this.menuItems.get(index);

				if (menuItem.isVisible()) {
					if (menuItem.isEnabled())
						new MenuLinkPanel(item, "menuItem", menuItem);
					else {
						new Label(item, "menuItem", menuItem.getModel()).setRenderBodyOnly(true);
						item.add(new AttributeModifier("class", true, new Model<String>("disabled")));
					}
				}
				else
					item.setVisible(false);
			}

		};
	}
}
