package wicket.contrib.dojo.examples;

import java.util.Date;

import wicket.PageParameters;
import wicket.contrib.dojo.toggle.DojoExplodeToggle;
import wicket.contrib.dojo.toggle.DojoFadeToggle;
import wicket.contrib.dojo.toggle.DojoWipeToggle;
import wicket.contrib.markup.html.form.DatePicker;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.model.Model;

public class DatePickerShower extends WebPage {
	
	private Date date1;
	private Date date2;
	private Date date3;
	private Date date4;
	private String text1;
		
	public DatePickerShower(PageParameters parameters){

		date1 = null;
		date2 = new Date();
		date3 = new Date();
		date4 = new Date(); 
		
		Form form = new Form("dateform");
		form.add(new DatePicker("date1", new Model(date1), "%m/%d/%Y"));
		
		DatePicker date2P = new DatePicker("date2", new Model(date2), "%m/%d/%Y");
		date2P.setToggle(new DojoWipeToggle(200));
		form.add(date2P);
		
		DatePicker date3P = new DatePicker("date3", new Model(date3), "%m/%d/%Y");
		date3P.setToggle(new DojoFadeToggle(600));
		form.add(date3P);
		
		DatePicker date4P = new DatePicker("date4", new Model(date4), "%m/%d/%Y");
		date4P.setToggle(new DojoExplodeToggle());
		form.add(date4P);

		this.add(form);
		
	}
}
