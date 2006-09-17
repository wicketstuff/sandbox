package wicket.contrib.scriptaculous.examples.autocomplete;

import wicket.MarkupContainer;
import wicket.contrib.scriptaculous.autocomplete.CustomLayoutAjaxAutocompleteTextField;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.model.CompoundPropertyModel;

public class CustomLayoutAjaxAutocompleteExamplePage extends WebPage
{

	private class AutocompleteExampleForm extends Form
	{
		public AutocompleteExampleForm(MarkupContainer parent, String id)
		{
			super(parent, id, new CompoundPropertyModel(new EmailSearchCommand()));

			new CustomLayoutAjaxAutocompleteTextField(this, "emailAddress",
					CustomLayoutAjaxAutocompleteExamplePageContribution.class);
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

	public CustomLayoutAjaxAutocompleteExamplePage()
	{
		super();
		new AutocompleteExampleForm(this, "searchForm");
	}
}
