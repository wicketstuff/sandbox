package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.CompressedResourceReference;
import org.apache.wicket.util.time.Duration;

public class RangeTextFieldDemo extends Panel {

	private static final long serialVersionUID = 1L;

	private static final CompressedResourceReference WICKET_AJAX_HTML5_JS = 
			new CompressedResourceReference(RangeTextFieldDemo.class, "wicket-ajax-html5.js");

	public RangeTextFieldDemo(final String id) {
		super(id);
		
		final RangeTextField<Double> rangeTextField = new RangeTextField<Double>("range");
		add(rangeTextField);
		
		rangeTextField.setMinimum(0.0d);
		rangeTextField.setMaximum(10.0d);
		rangeTextField.setStep(0.5d);
		
		final Label rangeLabel = new Label("rangeLabel", "");
		rangeLabel.setOutputMarkupId(true);
		add(rangeLabel);
		
		rangeTextField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				rangeLabel.setDefaultModel(rangeTextField.getModel());
				target.addComponent(rangeLabel);
			}
			
			@Override
			public void renderHead(IHeaderResponse response) {
				super.renderHead(response);
				// override Wicket.Form.serializeInput so that input[type=range] is serialized too
				response.renderJavascriptReference(WICKET_AJAX_HTML5_JS);
			}
		}.setThrottleDelay(Duration.milliseconds(500)));
	}
}
