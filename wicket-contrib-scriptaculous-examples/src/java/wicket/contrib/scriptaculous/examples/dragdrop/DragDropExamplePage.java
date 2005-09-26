package wicket.contrib.scriptaculous.examples.dragdrop;

import java.util.ArrayList;
import java.util.List;

import wicket.contrib.scriptaculous.Indicator;
import wicket.contrib.scriptaculous.dragdrop.DraggableImage;
import wicket.contrib.scriptaculous.dragdrop.DraggableTarget;
import wicket.markup.html.WebPage;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

public class DragDropExamplePage extends WebPage {

	public DragDropExamplePage() {
		Indicator indicator = new Indicator();
		final DraggableTarget cart = new DraggableTarget("cart", DragDropExamplePageContribution.class);
		cart.setIndicator(indicator);

		List results = new ArrayList();
		results.add(new CustomResultObject("product_123", "product.png"));
		results.add(new CustomResultObject("product_456", "product1.png"));
		add(new ListView("entry", results) {

			protected void populateItem(ListItem item) {
				CustomResultObject result = (CustomResultObject) item.getModelObject();

				DraggableImage image = new DraggableImage("product", result.getId(), result.getImage());
				cart.accepts(image);

				item.add(image);
			}
		});

		add(cart);
		add(indicator);
	}

	public class CustomResultObject {

		private final String id;
		private final String image;

		public CustomResultObject(String id, String image) {
			this.id = id;
			this.image = image;
		}

		public String getId()
		{
			return id;
		}

		public String getImage()
		{
			return image;
		}

	}

}
