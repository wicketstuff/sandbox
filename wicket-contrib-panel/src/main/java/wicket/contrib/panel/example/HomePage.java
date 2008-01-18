package wicket.contrib.panel.example;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import wicket.contrib.panel.Panel;
import wicket.contrib.panel.layout.TableLayoutManager;


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
        add(new Label("message", "If you see this message wicket is properly configured and running"));

        Panel panel = new Panel("panel");
        add(panel);

        // TODO Add your page's components here
        FormWithPanel formWithPanel = new FormWithPanel("form");
        panel.add(formWithPanel);

        final ValueMap properties = new ValueMap();
        final Panel formPanel = formWithPanel.getPanel();
        formPanel.add(new wicket.contrib.panel.example.Label("_text1", new /*Resource*/Model("text1")));
        formPanel.add(new TextField("text1", new PropertyModel(properties, "text1")));

        formPanel.add(new wicket.contrib.panel.example.Label("_text2", new /*Resource*/Model("text2")));
        formPanel.add(new TextField("text2", new PropertyModel(properties, "text2")));

        formPanel.add(new wicket.contrib.panel.example.Label("_result", new /*Resource*/Model("result")));
        formPanel.add(new wicket.contrib.panel.example.Label("result", new PropertyModel(properties, "result")));

        formPanel.add(new Button("button", new Model("concatenate")) {
        	@Override
        	public void onSubmit() {
        		properties.put("result", properties.getString("text1") + " - " + properties.getString("text2"));
        	}
        });
        formPanel.add(new Button("button2", new Model("debug")) {
        	@Override
        	public void onSubmit() {
        		boolean debug = ((TableLayoutManager)formPanel.getLayoutManager()).getDebug();
        		((TableLayoutManager)formPanel.getLayoutManager()).setDebug(!debug);
        		((TableLayoutManager)formPanel.getLayoutManager()).setBorder(!debug ? 1 : 0);
        	}
        });
        formPanel.add(new Button("button3", new Model("set 3 columns")) {
        	@Override
        	public void onSubmit() {
        		((TableLayoutManager)formPanel.getLayoutManager()).setColumns(3);
        	}
        });
        formPanel.add(new Button("button4", new Model("set 2 columns")) {
        	@Override
        	public void onSubmit() {
        		((TableLayoutManager)formPanel.getLayoutManager()).setColumns(2);
        	}
        });
    }
}
