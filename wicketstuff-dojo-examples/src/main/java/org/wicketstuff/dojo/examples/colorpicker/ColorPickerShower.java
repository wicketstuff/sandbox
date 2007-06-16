package org.wicketstuff.dojo.examples.colorpicker;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.form.DojoColorPicker;

public class ColorPickerShower extends WicketExamplePage {
	
	private String color;
		
	public ColorPickerShower(PageParameters parameters){

		color = "FF4554"; 
		
		Form form = new Form("form");
		
		DojoColorPicker colorPicker = new DojoColorPicker("colorPicker", new Model(color));
		form.add(colorPicker);

		this.add(form);
		
	}
}
