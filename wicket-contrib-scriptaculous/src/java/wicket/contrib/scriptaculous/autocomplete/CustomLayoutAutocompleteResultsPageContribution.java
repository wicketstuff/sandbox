package wicket.contrib.scriptaculous.autocomplete;

import wicket.PageParameters;
import wicket.markup.html.WebPage;

public class CustomLayoutAutocompleteResultsPageContribution extends WebPage
{

	private static final long serialVersionUID = 1L;
	private final String value;

	public CustomLayoutAutocompleteResultsPageContribution(PageParameters parameters)
	{
		String field = parameters.getString("fieldName");
		value = parameters.getString(field);
	}

	protected String getInputValue()
	{
		return value;
	}
}
