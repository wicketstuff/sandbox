package org.wicketstuff.dojo.examples.timepicker;

import java.util.Locale;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.form.DojoTimePicker;

public class TimePickerShower extends WicketExamplePage {
	
		
	public TimePickerShower(PageParameters parameters){

		Form form = new Form("timeform");
		
		DojoTimePicker date1P = new DojoTimePicker("time1", Locale.ENGLISH, null);
		date1P.setRequired(true);
		form.add(date1P);


		this.add(form);
		
	}
}
