package wicket.contrib.scriptaculous.examples.dragdrop;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.wicketstuff.scriptaculous.Indicator;
import org.wicketstuff.scriptaculous.dragdrop.DraggableImage;
import org.wicketstuff.scriptaculous.dragdrop.DraggableTarget;

public class DragDropExamplePage extends WebPage
{

	public DragDropExamplePage()
	{
		Indicator indicator = new Indicator();
		final DraggableTarget cart = new DraggableTarget("cart") {

			protected void onDrop(String input, AjaxRequestTarget target)
			{
				System.out.println("HERE WE ARE!");
				
			}
		};

		List results = new ArrayList();
		results.add(new CustomResultObject("product_123", "product.png"));
		results.add(new CustomResultObject("product_456", "product1.png"));
		add(new ListView("entry", results)
		{

			protected void populateItem(ListItem item)
			{
				CustomResultObject result = (CustomResultObject) item.getModelObject();

				DraggableImage image = new DraggableImage(result.getId(), result.getImage()) {

					protected String getStyleClass()
					{
						return "product";
					}};
				cart.accepts(image);

				item.add(image);
			}
		});

		add(cart);
		add(indicator);
	}

	public class CustomResultObject
	{

		private final String id;

		private final String image;

		public CustomResultObject(String id, String image)
		{
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
