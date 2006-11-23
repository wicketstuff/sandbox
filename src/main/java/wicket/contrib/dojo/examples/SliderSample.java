package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.markup.html.form.sliders.DojoIntegerSlider;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.model.Model;

public class SliderSample extends WebPage {
	
	private Integer int1;

		
	public SliderSample(PageParameters parameters){

		int1 = 12;
		
		Form form = new Form("form");
		DojoIntegerSlider slider = new DojoIntegerSlider("slider1", new Model(int1));
		slider.setStart(0);
		slider.setEnd(100);
		add(form);
		form.add(slider);

	}
}
