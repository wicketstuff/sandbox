package inputexample;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicket.contrib.input.events.EventType;
import wicket.contrib.input.events.InputBehavior;
import wicket.contrib.input.events.key.KeyType;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters) {

		// Add the simplest type of label
		add(new Label("message",
				"If you see this message wicket is properly configured and running"));

		// TODO Add your page's components here

		final IModel labelModel = new Model("nothing yet!");
		final Label label = new Label("id", labelModel);
		label.setOutputMarkupId(true);
		add(label);
		Form form = new Form("form"){@Override
		protected void onSubmit() {
			super.onSubmit();
			labelModel.setObject("form was submitted");
		}};
		add(form);
		Button button = new Button("button") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				labelModel.setObject("std btn was clicked");
			}
		}.setDefaultFormProcessing(false);
		List<KeyType> keys = new ArrayList<KeyType>();
		keys.add(KeyType.b);
		button.add(new InputBehavior(keys, EventType.click));

		keys = new ArrayList<KeyType>();
		keys.add(KeyType.a);

		form.add(new InputBehavior(keys, EventType.submit));
		form.add(button);
		Button button2 = new Button("button2").setDefaultFormProcessing(false);
		keys = new ArrayList<KeyType>();
		keys.add(KeyType.c);
		button2.add(new AjaxEventBehavior("onClick") {
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				labelModel.setObject("ajax was fired");
				target.addComponent(label);
			}
		});
		button2.add(new InputBehavior(keys, true));
		form.add(button2);

	}
}
