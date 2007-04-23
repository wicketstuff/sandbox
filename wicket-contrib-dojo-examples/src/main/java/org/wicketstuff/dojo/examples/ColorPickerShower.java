package org.wicketstuff.dojo.examples;

import org.apache.wicket.PageParameters;
import org.wicketstuff.dojo.markup.html.form.DojoColorPicker;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

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
