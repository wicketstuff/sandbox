package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.dojo.markup.html.form.sliders.DojoIntegerSlider;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.model.PropertyModel;

public class SliderSample extends WebPage {
    private int value = 12;

    public SliderSample(PageParameters params) throws Exception {
        if (params.containsKey("value"))
            value = params.getInt("value");
        Form form = new Form("form") {
            protected void onSubmit() {
                PageParameters params = new PageParameters();
                params.add("value", Integer.toString(value));
                setResponsePage(SliderSample.class, params);
            }
        };
        DojoIntegerSlider slider = new DojoIntegerSlider("slider1", new PropertyModel(this, "value"));
        slider.setStart(0);
        slider.setEnd(100);
        add(form);
        form.add(slider);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
