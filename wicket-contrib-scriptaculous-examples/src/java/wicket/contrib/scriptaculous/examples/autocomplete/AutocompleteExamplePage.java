package wicket.contrib.scriptaculous.examples.autocomplete;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.scriptaculous.autocomplete.AjaxAutocompleteBehavior;
import org.wicketstuff.scriptaculous.autocomplete.AutocompleteBehavior;

public class AutocompleteExamplePage extends WebPage {

	public AutocompleteExamplePage() {
		super();
		
		String[] results = new String[] {
				"red"
				, "rose"
				, "red-green"
				, "green"
				, "grey"
				, "gray"
				, "blue"
				, "black"
				, "purple"
				, "pink"
				, "orange"
				, "off-white"
				, "white"
				, "yellow"
				, "yellow-green"
		};
		TextField color = new TextField("color");
		color.add(new AutocompleteBehavior(results));
		add(color);
		
		TextField email = new TextField("email", new PropertyModel(new User(), "email"));
		email.add(new AjaxAutocompleteBehavior() {
			protected String[] getResults(String input) {
				return new String[] {
						"bill.gates@microsoft.com", 
						"me@yourdomain.com", 
						"ryan@codecrate.com",
						};
			}
		});
		add(email);
	}
	
	private static class User implements Serializable {
		private String email;

		public String getEmail()
		{
			return email;
		}

		public void setEmail(String email)
		{
			this.email = email;
		}
	}
}
