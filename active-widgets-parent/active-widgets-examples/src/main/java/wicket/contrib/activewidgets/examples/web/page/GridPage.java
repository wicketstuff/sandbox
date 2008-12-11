package wicket.contrib.activewidgets.examples.web.page;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import wicket.contrib.activewidgets.examples.ContactDao;
import wicket.contrib.activewidgets.examples.web.ContactsDataProvider;
import wicket.contrib.activewidgets.grid.GridColumns;
import wicket.contrib.activewidgets.grid.GridExtended;
import wicket.contrib.activewidgets.system.ActiveWidgetsConfiguration;


public class GridPage extends WicketExamplePage {
	private static final long serialVersionUID = 1L;
	private final int width = 300;
	private final int height = 200;
	private final int rowCount = 20;
	private final boolean selectorVisible = true;

	@SpringBean(name = "contactDao")
	private ContactDao dao;
	
	public GridPage() {
		super();

		String isArgumentGiven = System.getProperty(ActiveWidgetsConfiguration.KEY_AW_LICENSE);
		if (isArgumentGiven == null && !ActiveWidgetsConfiguration.isTrial()) {
			ActiveWidgetsConfiguration.setLicenseType(ActiveWidgetsConfiguration.AW_TRIAL_LICENSE);
		}
		
		// set up data provider
		ContactsDataProvider dataProvider = new ContactsDataProvider(dao);
 		GridColumns columns = createColumns();

		GridExtended grid;
 		add(grid = new GridExtended("grid", columns, dataProvider)
				.setWidth(width)
				.setHeight(height)
				.setSelectorVisible(selectorVisible)
				.setRowCount(rowCount)
		);
		
		Form form;
		
		add(form = new Form("form"));
		form.add(new TextField("width", new PropertyModel(grid, "width.value")));
		form.add(new TextField("height", new PropertyModel(grid, "height.value")));
		form.add(new TextField("rowCount", new PropertyModel(grid, "rowCount.value")));
		form.add(new CheckBox("selectorVisible", new PropertyModel(grid, "selectorVisible.value")));
		form.add(new CheckBox("cellEditable", new PropertyModel(grid, "cellEditable.value")));
	}

	private GridColumns createColumns() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
