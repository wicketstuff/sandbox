package org.wicketstuff.html5.markup.html.form;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

public class UrlTextFieldDemo extends Panel {

	private static final long serialVersionUID = 1L;

	private static final List<URL> CHOICES = new ArrayList<URL>();
	static {
		try {
			CHOICES.add(new URL("http://www.google.com"));
			CHOICES.add(new URL("http://www.yahoo.com"));
		} catch (final Exception x) {
			throw new RuntimeException(x);
		}
	}
	
	public UrlTextFieldDemo(final String wid) throws Exception {
		super(wid);
		
		final Datalist<URL> datalist = new Datalist<URL>("datalist", new Model<URL>(), CHOICES);
		add(datalist);
		
		final UrlTextField urlTextField = new UrlTextField("url", datalist.getMarkupId());
		add(urlTextField);
		
		final Label rangeLabel = new Label("urlLabel", "");
		rangeLabel.setOutputMarkupId(true);
		add(rangeLabel);
		
		urlTextField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				rangeLabel.setDefaultModel(urlTextField.getModel());
				target.addComponent(rangeLabel);
			}
		}.setThrottleDelay(Duration.milliseconds(500)));
	}
}
