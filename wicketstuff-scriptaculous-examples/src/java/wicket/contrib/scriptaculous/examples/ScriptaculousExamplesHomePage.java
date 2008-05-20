package wicket.contrib.scriptaculous.examples;

import wicket.contrib.scriptaculous.examples.autocomplete.AutocompleteExamplePage;
import wicket.contrib.scriptaculous.examples.dragdrop.DragDropExamplePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class ScriptaculousExamplesHomePage extends WebPage {

	public ScriptaculousExamplesHomePage() {
		add(new BookmarkablePageLink("autocompleteExampleLink", AutocompleteExamplePage.class));

		add(new BookmarkablePageLink("draggableImageExamplePage", DragDropExamplePage.class));
	}
}
