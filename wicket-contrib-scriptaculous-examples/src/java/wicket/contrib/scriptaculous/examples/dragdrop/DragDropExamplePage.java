package wicket.contrib.scriptaculous.examples.dragdrop;

import wicket.contrib.scriptaculous.dragdrop.DraggableImage;
import wicket.contrib.scriptaculous.dragdrop.DraggableTarget;
import wicket.markup.html.WebPage;

public class DragDropExamplePage extends WebPage {

	public DragDropExamplePage() {
		add(new DraggableImage("product", "product_123", "product.png", "products"));
		add(new DraggableTarget("cart", "products", DragDropExamplePageContribution.class));
	}

}
