package wicket.contrib.panel.example;

import org.apache.wicket.model.IModel;

import wicket.contrib.panel.Form;
import wicket.contrib.panel.LayoutManager;
import wicket.contrib.panel.Panel;
import wicket.contrib.panel.layout.TableLayoutManager;


public class FormWithPanel extends Form {

	protected final Panel panel;
  public static final String FORM_PANEL_NAME = "formPanel";

  public static final int COLUMN_WIDTH = 220;

  public FormWithPanel(String id) {
  	this(id, null, null);
  }

	/**
	 * Construct.
	 *
	 * @param id
	 *            See Component
	 * @param model
	 *            Must contain a Integer model object
	 * @see wicket.Component#Component(String, IModel)
	 */
	public FormWithPanel(final String id, final IModel model) {
		this(id, model,  null);
	}

	public LayoutManager createLayoutManager() {
		TableLayoutManager layoutManager = new TableLayoutManager(2).setColumnWidth(COLUMN_WIDTH, 1, -1);
		layoutManager.setRowStyle("form", -1);
		return layoutManager;
	}

	public FormWithPanel(final String id, final IModel model, LayoutManager layoutManager) {
		super(id, model);
		if (layoutManager == null)
			layoutManager = createLayoutManager();
    panel = new Panel(FORM_PANEL_NAME, model, layoutManager);
    add(panel);
	}

  public Panel getPanel() {
  	return panel;
  }


}
