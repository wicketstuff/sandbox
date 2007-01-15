package wicket.contrib.scriptaculous.examples.dragdrop;

import java.util.ArrayList;
import java.util.List;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.scriptaculous.Indicator;
import wicket.contrib.scriptaculous.dragdrop.DraggableImage;
import wicket.contrib.scriptaculous.dragdrop.DraggableTarget;
import wicket.markup.html.WebPage;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

/**
 * Example page for DnD.
 */
public class DragDropExamplePage extends WebPage
{

	/**
	 * Construct.
	 */
	public DragDropExamplePage()
	{
		Indicator indicator = new Indicator(this);
		final DraggableTarget cart = new DraggableTarget(this, "cart")
		{
			@Override
			protected void onDrop(String input, AjaxRequestTarget target)
			{
			}
		};

		List<CustomResultObject> results = new ArrayList<CustomResultObject>();
		results.add(new CustomResultObject("product_123", "product.png"));
		results.add(new CustomResultObject("product_456", "product1.png"));
		new ListView<CustomResultObject>(this, "entry", results)
		{

			protected void populateItem(ListItem<CustomResultObject> item)
			{
				CustomResultObject result = (CustomResultObject) item.getModelObject();

				DraggableImage image = new DraggableImage(item, "product", result
						.getImage())
				{
					@Override
					protected String getStyleClass()
					{
						return "";
					}
				};
				cart.accepts(image);
			}
		};
	}

	/**
	 * Result object.
	 */
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
