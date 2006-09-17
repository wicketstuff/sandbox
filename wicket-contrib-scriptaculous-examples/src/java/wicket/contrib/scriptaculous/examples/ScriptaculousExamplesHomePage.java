package wicket.contrib.scriptaculous.examples;

import wicket.contrib.scriptaculous.examples.autocomplete.AjaxAutocompleteExamplePage;
import wicket.contrib.scriptaculous.examples.autocomplete.AutocompleteExamplePage;
import wicket.contrib.scriptaculous.examples.autocomplete.CustomLayoutAjaxAutocompleteExamplePage;
import wicket.contrib.scriptaculous.examples.dragdrop.DragDropExamplePage;
import wicket.markup.html.WebPage;
import wicket.markup.html.link.BookmarkablePageLink;

public class ScriptaculousExamplesHomePage extends WebPage {

	public ScriptaculousExamplesHomePage() {
		new BookmarkablePageLink(this, "autocompleteExampleLink", AutocompleteExamplePage.class);
		new BookmarkablePageLink(this, "ajaxAutocompleteExampleLink", AjaxAutocompleteExamplePage.class);
		new BookmarkablePageLink(this, "customLayoutAjaxAutocompleteExampleLink", CustomLayoutAjaxAutocompleteExamplePage.class);

		new BookmarkablePageLink(this, "draggableImageExamplePage", DragDropExamplePage.class);
	}
}
