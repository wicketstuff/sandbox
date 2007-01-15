package wicket.contrib.scriptaculous.examples.autocomplete;

import java.util.Arrays;

import wicket.MarkupContainer;
import wicket.contrib.scriptaculous.autocomplete.AutocompleteTextField;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.model.CompoundPropertyModel;

/**
 * Example for auto complete.
 */
public class AutocompleteExamplePage extends WebPage
{

	/**
	 * Construct.
	 */
	public AutocompleteExamplePage()
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

			String[] results = new String[] {"ryan sonnek", "bill gates", "alan johnson"};
			new AutocompleteTextField<String>(this, "emailAddress", Arrays
					.asList(results));
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
