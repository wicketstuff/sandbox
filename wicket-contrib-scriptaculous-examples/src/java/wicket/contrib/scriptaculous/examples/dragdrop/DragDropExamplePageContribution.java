package wicket.contrib.scriptaculous.examples.dragdrop;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import wicket.PageParameters;
import wicket.contrib.scriptaculous.dragdrop.DragDropPageContribution;
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
		List cartItems = (List) getSessionInternal().getAttribute("cartItems");
		if (null == cartItems) {
			cartItems = new ArrayList();
			getSessionInternal().setAttribute("cartItems", cartItems);
		}

		return cartItems;

	}

	private HttpSession getSessionInternal() {
		return ((WebSession)getSession()).getHttpSession();
	}
}
