package wicket.contrib.scriptaculous.examples.dragdrop;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import wicket.PageParameters;
import wicket.contrib.scriptaculous.dragdrop.DragDropPageContribution;
import wicket.contrib.scriptaculous.examples.ScriptaculousExamplesSession;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.protocol.http.WebSession;

public class DragDropExamplePageContribution extends DragDropPageContribution {

	public DragDropExamplePageContribution(PageParameters parameters) {
		super(parameters);

		if (null != getInputValue()) {
			addProductToCart();
		}

		add(new ListView("cartItem", getCartItems()){

			protected void populateItem(ListItem item) {
				item.add(new Label("productId", item.getModelObjectAsString()));
			}});
	}

	private void addProductToCart() {
		//simulate server processing
		//allow scriptaculous indicator to display
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getCartItems().add(getInputValue());
	}

	private List getCartItems() {
		ScriptaculousExamplesSession session = (ScriptaculousExamplesSession)getSession();
		List cartItems = (List) session.getCartItems();
		if (null == cartItems) {
			cartItems = new ArrayList();
			session.setCartItems(cartItems);
		}

		return cartItems;

	}
}
