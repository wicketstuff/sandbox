package wicket.contrib.scriptaculous.examples.autocomplete;

import wicket.contrib.scriptaculous.autocomplete.AjaxAutocompleteTextField;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.model.CompoundPropertyModel;

public class AjaxAutocompleteExamplePage extends WebPage
{

	public AjaxAutocompleteExamplePage()
	{
		super();
		add(new AutocompleteExampleForm("searchForm"));
	}

	private class EmailSearchCommand
	{
		private String emailAddress;

		public String getEmailAddress()
		{
			return emailAddress;
		}

		public void setEmailAddress(String emailAddress)
		{
			this.emailAddress = emailAddress;
		}

	}

	private class AutocompleteExampleForm extends Form
	{
		public AutocompleteExampleForm(String id)
		{
			super(id, new CompoundPropertyModel(new EmailSearchCommand()));

			add(new AjaxAutocompleteTextField("emailAddress")
			{
				public String[] getResults(String input)
				{
					return new String[] {"bill.gates@microsoft.com", "me@yourdomain.com"};
				}
			});
			add(new Button("submitButton"));
		}

		protected void onSubmit()
		{
			// do something here
		}
	}
}
