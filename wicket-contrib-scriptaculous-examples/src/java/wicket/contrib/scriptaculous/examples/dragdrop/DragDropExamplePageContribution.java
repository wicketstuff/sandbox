package wicket.contrib.scriptaculous.examples.dragdrop;

import wicket.PageParameters;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;

public class DragDropExamplePageContribution extends WebPage {

	public DragDropExamplePageContribution(PageParameters parameters) {
		String input = parameters.getString("id");
		add(new Label("productId", input));
	}
}
