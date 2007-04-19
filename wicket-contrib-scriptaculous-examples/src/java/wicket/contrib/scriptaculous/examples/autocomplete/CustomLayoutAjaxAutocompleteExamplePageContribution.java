package wicket.contrib.scriptaculous.examples.autocomplete;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import wicket.contrib.scriptaculous.autocomplete.CustomLayoutAutocompleteResultsPageContribution;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class CustomLayoutAjaxAutocompleteExamplePageContribution extends CustomLayoutAutocompleteResultsPageContribution {

	public CustomLayoutAjaxAutocompleteExamplePageContribution(PageParameters parameters) {
		super(parameters);

		List results = new ArrayList();
		results.add(new CustomResultObject("ryan.gif", "Ryan Sonnek", "ryan@youremail.com"));
		results.add(new CustomResultObject("billy.gif", "Bill Gates", "bill.gates@microsoft.com"));
		results.add(new CustomResultObject("janet.gif", "Janet Someone", "janet@thethirdwheel.com"));
		add(new ListView("entry", results) {

			protected void populateItem(ListItem item) {
				CustomResultObject result = (CustomResultObject) item.getModelObject();

				item.add(new Label("name", result.getName()));
				item.add(new Label("email", result.getEmail()));
			}
		});
	}

	private class CustomResultObject {

		private final String name;
		private final String image;
		private final String email;

		public CustomResultObject(String image, String name, String email) {
			this.image = image;
			this.name = name;
			this.email = email;
		}

		public String getEmail()
		{
			return email;
		}

		public String getImage()
		{
			return image;
		}

		public String getName()
		{
			return name;
		}
	}
}


