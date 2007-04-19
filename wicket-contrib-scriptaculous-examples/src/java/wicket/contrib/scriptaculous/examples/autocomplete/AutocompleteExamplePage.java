package wicket.contrib.scriptaculous.examples.autocomplete;

import wicket.contrib.scriptaculous.autocomplete.AutocompleteTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

public class AutocompleteExamplePage extends WebPage {

	public AutocompleteExamplePage() {
		super();
		add(new AutocompleteExampleForm("searchForm"));
	}

	private class EmailSearchCommand {
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

	private class AutocompleteExampleForm extends Form {
		public AutocompleteExampleForm(String id) {
			super(id, new CompoundPropertyModel(new EmailSearchCommand()));

			String[] results = new String[] {
					"ryan sonnek"
					, "bill gates"
					, "alan johnson"
			};
			add(new AutocompleteTextField("emailAddress", results));
			add(new Button("submitButton"));
		}

		protected void onSubmit() {
			//do something here
		}
	}
}
