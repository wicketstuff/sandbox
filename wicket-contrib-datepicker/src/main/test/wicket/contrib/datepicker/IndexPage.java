package wicket.contrib.datepicker;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import wicket.contrib.datepicker.datepicker.DatepickerField;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.model.PropertyModel;

public class IndexPage extends WebPage {
	
	Date date=new Date();
	
	public Date getDate(){
		return date;
	}
	
	public void setDate(Date date){
		this.date=date;
	}
	
	public IndexPage() {
		Form form=new Form("form");
		add(form);
		form.add(new DatepickerField("dp",new PropertyModel(this,"date"),getLocale()));
		List locales=Arrays.asList(Locale.getAvailableLocales());
		form.add(new DropDownChoice("locale",new PropertyModel(getSession(),"locale"),locales));
	}
}
