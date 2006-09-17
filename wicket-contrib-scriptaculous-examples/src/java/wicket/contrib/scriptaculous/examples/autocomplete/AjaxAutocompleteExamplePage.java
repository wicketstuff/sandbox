package wicket.contrib.scriptaculous.examples.autocomplete;

import wicket.MarkupContainer;
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
		new AutocompleteExampleForm(this, "searchForm");
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
		public AutocompleteExampleForm(MarkupContainer parent, String id)
		{
			super(parent, id, new CompoundPropertyModel(new EmailSearchCommand()));

			new AjaxAutocompleteTextField(this, "emailAddress")
			{
				public String[] getResults(String input)
				{
					return new String[] {"bill.gates@microsoft.com", "me@yourdomain.com"};
				}
			};
			new Button(this, "submitButton")
			{
				@Override
				protected void onSubmit()
				{
				}
			};
		}

		protected void onSubmit()
		{
			// do something here
		}
	}
}
