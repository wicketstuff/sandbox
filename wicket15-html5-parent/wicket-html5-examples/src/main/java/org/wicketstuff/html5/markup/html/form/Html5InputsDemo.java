package org.wicketstuff.html5.markup.html.form;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.html5.BasePage;

public class Html5InputsDemo extends BasePage {

	private static final String PANEL_ID = "input";
	
	private final Pojo pojo;
	
	public Html5InputsDemo(final PageParameters parameters) throws Exception {
		super(parameters);
		
		this.pojo= new Pojo();
		pojo.setRange(2.0d);
		pojo.setUrl(new URL("http://www.wicketstuff.org"));
		
		final IModel<Pojo> formModel = new CompoundPropertyModel<Pojo>(pojo);
		final Form<Pojo> form = new Form<Pojo>("form", formModel);
		add(form);
		
		final List<Panel> inputs = new ArrayList<Panel>();
		inputs.add(new UrlTextFieldDemo(PANEL_ID));
		inputs.add(new RangeTextFieldDemo(PANEL_ID));
		
		final ListView<Panel> inputsListView = new ListView<Panel>("inputs", inputs) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Panel> item) {
				final Panel inputDemoPanel = item.getModelObject();
				item.add(inputDemoPanel);
			}
		};
		
		form.add(inputsListView);
	}
}
