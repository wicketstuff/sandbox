package wicket.contrib.scriptaculous.autocomplete;

import wicket.PageParameters;
import wicket.markup.html.WebPage;
import wicket.markup.html.list.ListView;

public abstract class CustomLayoutAutocompleteResultsPageContribution extends WebPage {

	public CustomLayoutAutocompleteResultsPageContribution(PageParameters parameters) {
		String field = parameters.getString("fieldName");
		String value = parameters.getString(field);

		add(buildListView(value));
	}

	protected abstract ListView buildListView(String input);
}
