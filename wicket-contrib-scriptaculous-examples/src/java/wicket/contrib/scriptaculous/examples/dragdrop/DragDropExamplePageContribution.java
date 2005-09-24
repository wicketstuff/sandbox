package wicket.contrib.scriptaculous.examples.dragdrop;

import wicket.PageParameters;
import wicket.contrib.scriptaculous.dragdrop.DragDropPageContribution;
import wicket.markup.html.basic.Label;

public class DragDropExamplePageContribution extends DragDropPageContribution {

	public DragDropExamplePageContribution(PageParameters parameters) {
		super(parameters);
		add(new Label("productId", getInputValue()));
	}
}
