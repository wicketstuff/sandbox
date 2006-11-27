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
import wicket.contrib.markup.html.form.sliders.DojoIntegerSlider;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.model.IModel;
import wicket.model.Model;

public class SliderSample extends WebPage {
	
	private Integer int1;

		
	public SliderSample(PageParameters parameters){

		int1 = 12;
		
		Form form = new Form(this, "form");
		DojoIntegerSlider slider = new DojoIntegerSlider(form, "slider1", new Model<Integer>(int1));
		slider.setStart(0);
		slider.setEnd(100);

	}
}
