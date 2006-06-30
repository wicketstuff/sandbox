package wicket.contrib.datepicker;

import wicket.contrib.datepicker.datepicker.DatepickerField;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;

public class IndexPage extends WebPage {
	public IndexPage() {
		Form form=new Form("form");
		add(form);
		form.add(new DatepickerField("dp"));
	}
}
