package org.wicketstuff.scriptaculous.autocomplete;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;

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
