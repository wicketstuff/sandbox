package wicket.contrib.dojo.examples;

import java.util.Date;

import wicket.MarkupContainer;
import wicket.PageParameters;
import wicket.Response;
import wicket.contrib.dojo.AbstractDojoTimerBehavior;
import wicket.contrib.dojo.toggle.DojoExplodeToggle;
import wicket.contrib.dojo.toggle.DojoFadeToggle;
import wicket.contrib.dojo.toggle.DojoWipeToggle;
import wicket.contrib.markup.html.form.DatePicker;
import wicket.contrib.markup.html.form.ImmediateCheckBox;
import wicket.contrib.markup.html.form.ImmediateTextField;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.model.IModel;
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
		
		Form form = new Form(this, "dateform");
		new DatePicker(form, "date1", new Model<Date>(date1), "%m/%d/%Y");
		new DatePicker(form, "date2", new Model<Date>(date2), "%m/%d/%Y").setToggle(new DojoWipeToggle(200));
		new DatePicker(form, "date3", new Model<Date>(date3), "%m/%d/%Y").setToggle(new DojoFadeToggle(600));
		new DatePicker(form, "date4", new Model<Date>(date4), "%m/%d/%Y").setToggle(new DojoExplodeToggle());

	}
}
