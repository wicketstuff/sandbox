package wicket.contrib.activewidgets.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.template.PackagedTextTemplate;

import wicket.contrib.activewidgets.ActiveWidgetsComponent;
import wicket.contrib.activewidgets.system.SizeUnit;

public class GridExtended extends ActiveWidgetsComponent {

	@SuppressWarnings("unused")
	private GridColumns columns;

	public GridExtended(String id, IModel model) {
		super(id, model);
	}

	public GridExtended(String id) {
		super(id);
	}

	public GridExtended(String id, GridColumns columns,
			IDataProvider dataProvider) {
		super(id, new Model(dataProvider));
		this.columns = columns;
	}

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_GRID_WIDTH = "300px";

	private static final String DEFAULT_GRID_HEIGHT = "200px";

	transient private StyleStyleToken width = new StyleStyleToken(DEFAULT_GRID_WIDTH) {
		public String getTokenName() {
			return "width";
		}
	};

	transient private StyleStyleToken height = new StyleStyleToken (DEFAULT_GRID_HEIGHT) {
		public String getTokenName() {
			return "height";
		}
	};


	
	transient private JavascriptToken selectorVisible = new JavascriptToken(JS_MITTELSPIEL) {
		public String getTokenName() {
			return "setSelectorVisible";
		}
	};

	transient private JavascriptToken rowCount = new JavascriptToken(JS_MITTELSPIEL) {
		public String getTokenName() {
			return "setRowCount";
		}
	};

	transient private JavascriptToken cellEditable = new JavascriptToken(JS_MITTELSPIEL) {
		public String getTokenName() {
			return "setCellEditable";
		}
	};
	
	/**
	 * 
	 * @return the initilization javascript
	 */
	protected List<Token> javascriptContributors()
	{
		final List<Token> javascriptContributors = new ArrayList<Token>();
		Map<String, Object> variables = new HashMap<String, Object>(4);
		variables.put("javaScriptId", activeWidgetsId);
		variables.put("elementId", activeWidgetsId);
		variables.put("activeWidgetsId", activeWidgetsId);

		PackagedTextTemplate template = new PackagedTextTemplate(GridExtended.class, "init.js");
		template.interpolate(variables);
		
		javascriptContributors.add(new ConstructorToken(JS_DEBUT, "AW.Grid.Extended") {});
		javascriptContributors.add(new JavascriptToken(JS_DEBUT + 1, "\"" + activeWidgetsId + "\"") {
			public String getTokenName() {
				return "setId";
			}
			
		});
		javascriptContributors.add(new Token(JS_DEBUT + 2, template.getString()){
			public String getToken() {
				return value;
			}
			public String getTokenName() {
				return null;
			}
		});
		javascriptContributors.add(selectorVisible);
		javascriptContributors.add(rowCount);
		javascriptContributors.add(cellEditable);
		

		return javascriptContributors;
	}

	protected List<Token> styleContributors()
	{
		final List<Token> styleContributors = new ArrayList<Token>();
		styleContributors.add(this.width);
		styleContributors.add(this.height);
		return styleContributors;
	}

	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();

		// initialize lazily
		if (activeWidgetsId == null) {
			// assign the markup id
			String id = capitalize(getMarkupId());
//			domId = "dom" + id;
//			activeWidgetsId = "var" + id;
			activeWidgetsId = "aw" + id;
		}
	}


	public GridExtended setWidth(int width) {
		this.width.setValue(new Integer(width).toString(), SizeUnit.px);
		return this;
	}


	public GridExtended setHeight(int height) {
		this.height.setValue(new Integer(height).toString(), SizeUnit.px);
		return this;
	}


	public GridExtended setSelectorVisible(boolean visible) {
		this.selectorVisible.setValue(new Boolean(visible).toString());
		return this;
	}


	public GridExtended setRowCount(int count) {
		this.rowCount.setValue(new Integer(count).toString());
		return this;
	}

	public GridExtended setCellEditable(boolean editable) {
		this.selectorVisible.setValue(new Boolean(editable).toString());
		return this;
	}

}
