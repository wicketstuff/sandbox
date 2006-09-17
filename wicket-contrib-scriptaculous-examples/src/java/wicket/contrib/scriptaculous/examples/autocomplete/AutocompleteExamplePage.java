package wicket.contrib.scriptaculous.examples.autocomplete;

import wicket.MarkupContainer;
import wicket.contrib.scriptaculous.autocomplete.AutocompleteTextField;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.model.CompoundPropertyModel;

public class AutocompleteExamplePage extends WebPage
{

	public AutocompleteExamplePage()
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

			String[] results = new String[] {"ryan sonnek", "bill gates", "alan johnson"};
			new AutocompleteTextField(this, "emailAddress", results);
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
