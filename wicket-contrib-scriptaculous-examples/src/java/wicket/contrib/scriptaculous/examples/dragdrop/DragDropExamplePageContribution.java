package wicket.contrib.scriptaculous.examples.dragdrop;

import wicket.PageParameters;
import wicket.markup.html.WebPage;

public class DragDropExamplePageContribution extends WebPage {

	public DragDropExamplePageContribution(PageParameters parameters) {
		String input = parameters.getString("id");
		System.out.println(input);
	}
}
