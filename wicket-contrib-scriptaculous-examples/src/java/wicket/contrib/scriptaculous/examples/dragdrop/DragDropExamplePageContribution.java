package wicket.contrib.scriptaculous.examples.dragdrop;

import java.util.ArrayList;
import java.util.List;

import wicket.PageParameters;
import wicket.contrib.scriptaculous.examples.ScriptaculousExamplesSession;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

/**
 * Page.
 */
public class DragDropExamplePageContribution extends WebPage
{
	/**
	 * Construct.
	 * 
	 * @param parameters
	 */
	public DragDropExamplePageContribution(PageParameters parameters)
	{
		super(parameters);

		addProductToCart();

		new ListView(this, "cartItem", getCartItems())
		{

			protected void populateItem(ListItem item)
			{
				new Label(item, "productId", item.getModelObjectAsString());
			}
		};
	}

	private void addProductToCart()
	{
		// simulate server processing
		// allow scriptaculous indicator to display
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		getCartItems().add(getInputValue());
	}

	private List getCartItems()
	{
		ScriptaculousExamplesSession session = (ScriptaculousExamplesSession) getSession();
		List cartItems = (List) session.getCartItems();
		if (null == cartItems)
		{
			cartItems = new ArrayList();
			session.setCartItems(cartItems);
		}

		return cartItems;

	}
}
