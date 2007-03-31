package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.dojo.markup.html.form.DojoColorPicker;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.model.Model;

public class ColorPickerShower extends WebPage {
	
	private String color;
		
	public ColorPickerShower(PageParameters parameters){

		color = "FF4554"; 
		
		Form form = new Form("form");
		
		DojoColorPicker colorPicker = new DojoColorPicker("colorPicker", new Model(color));
		form.add(colorPicker);

		this.add(form);
		
	}
}
