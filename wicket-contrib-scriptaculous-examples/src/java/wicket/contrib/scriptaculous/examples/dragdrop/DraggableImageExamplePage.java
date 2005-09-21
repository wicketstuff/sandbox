package wicket.contrib.scriptaculous.examples.dragdrop;

import wicket.contrib.scriptaculous.dragdrop.DraggableImage;
import wicket.markup.html.WebPage;

public class DraggableImageExamplePage extends WebPage {

	public DraggableImageExamplePage() {
		add(new DraggableImage("image", "product.png"));
	}

}
