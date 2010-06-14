package org.wicketstuff.html5.markup.html.form;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CompressedResourceReference;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.html5.BasePage;

public class UrlTextFieldDemo extends BasePage {

		private static final CompressedResourceReference WICKET_AJAX_HTML5_JS = 
				new CompressedResourceReference(UrlTextFieldDemo.class, "wicket-ajax-html5.js");

		private static final List<URL> CHOICES = new ArrayList<URL>();
		static {
			try {
				CHOICES.add(new URL("http://www.google.com"));
				CHOICES.add(new URL("http://www.yahoo.com"));
			} catch (final Exception x) {
				throw new RuntimeException(x);
			}
		}
		
		private final URL url;
		
		public UrlTextFieldDemo(final PageParameters parameters) throws Exception {
			super(parameters);
			
			this.url = new URL("http://abv.bg");
			
			Form<Void> form = new Form<Void>("form");
			add(form);
			
			Datalist<URL> datalist = new Datalist<URL>("datalist", new Model<URL>(), CHOICES);
			form.add(datalist);
			
			UrlTextField urlTextField = new UrlTextField("url", Model.of(url));
			form.add(urlTextField);
			urlTextField.setDatalist(datalist);
			
			final Label rangeLabel = new Label("urlLabel", new Model<String>(url.toString()));
			rangeLabel.setOutputMarkupId(true);
			add(rangeLabel);
			
			urlTextField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					rangeLabel.setDefaultModelObject(url.toString());
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
