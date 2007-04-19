package wicket.contrib.scriptaculous.autocomplete;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;

public class CustomLayoutAutocompleteResultsPageContribution extends WebPage {

	private final String value;

	public CustomLayoutAutocompleteResultsPageContribution(PageParameters parameters) {
		String field = parameters.getString("fieldName");
		value = parameters.getString(field);
	}

	protected String getInputValue() {
		return value;
	}
}
