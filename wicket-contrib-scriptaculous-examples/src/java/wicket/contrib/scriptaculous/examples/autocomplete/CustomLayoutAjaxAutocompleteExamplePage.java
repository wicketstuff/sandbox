package wicket.contrib.scriptaculous.examples.autocomplete;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.scriptaculous.autocomplete.CustomLayoutAjaxAutocompleteTextField;

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
