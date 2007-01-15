package wicket.contrib.scriptaculous.examples.autocomplete;

import java.util.Arrays;
import java.util.Collection;

import wicket.MarkupContainer;
import wicket.contrib.scriptaculous.autocomplete.AjaxAutocompleteTextField;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.model.CompoundPropertyModel;

/**
 * Example.
 */
public class AjaxAutocompleteExamplePage extends WebPage
{

	/**
	 * Construct.
	 */
	public AjaxAutocompleteExamplePage()
	{
		super();
		new AutocompleteExampleForm(this, "searchForm");
	}

	private class EmailSearchCommand
	{
		private String emailAddress;

		/**
		 * @return email address
		 */
		public String getEmailAddress()
		{
			return emailAddress;
		}

		/**
		 * @param emailAddress
		 */
		public void setEmailAddress(String emailAddress)
		{
			this.emailAddress = emailAddress;
		}

	}

	private class AutocompleteExampleForm extends Form<EmailSearchCommand>
	{
		/**
		 * @param parent
		 * @param id
		 */
		public AutocompleteExampleForm(MarkupContainer parent, String id)
		{
			super(parent, id, new CompoundPropertyModel<EmailSearchCommand>(
					new EmailSearchCommand()));

			new AjaxAutocompleteTextField(this, "emailAddress")
			{
				public Collection<String> getResults(String input)
				{
					return Arrays.asList(new String[] {"bill.gates@microsoft.com",
							"me@yourdomain.com"});
				}
			};
			new Button(this, "submitButton")
			{
				@Override
				public void onSubmit()
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
