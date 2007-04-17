package wicket.contrib.scriptaculous.examples.autocomplete;

import wicket.contrib.scriptaculous.autocomplete.CustomLayoutAjaxAutocompleteTextField;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.model.CompoundPropertyModel;

public class CustomLayoutAjaxAutocompleteExamplePage extends WebPage {

	public CustomLayoutAjaxAutocompleteExamplePage() {
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

			add(new CustomLayoutAjaxAutocompleteTextField("emailAddress", CustomLayoutAjaxAutocompleteExamplePageContribution.class));
			add(new Button("submitButton"));
		}

		protected void onSubmit() {
			//do something here
		}
	}
}
