package wicket.contrib.activewidgets.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.template.PackagedTextTemplate;

import wicket.contrib.activewidgets.ActiveWidgetsComponent;

public class Grid extends ActiveWidgetsComponent {

	public Grid(String id, IModel model) {
		super(id, model);
	}

	public Grid(String id) {
		super(id);
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
	
	
	/**
	 * 
	 * @return the initilization javascript
	 */
	protected List<Token> javascriptContributors()
	{
		final List<Token> javascriptContributors = new ArrayList<Token>();
		Map<String, Object> variables = new HashMap<String, Object>(4);
		variables.put("javaScriptId", varId);
		variables.put("elementId", domId);
		variables.put("activeWidgetsId", activeWidgetsId);

		PackagedTextTemplate template = new PackagedTextTemplate(Grid.class, "init.js");
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
		javascriptContributors.add(new DocumentWrite(JS_MATT, varId) {});
		javascriptContributors.add(selectorVisible);
		javascriptContributors.add(rowCount);
		

		return javascriptContributors;
	}

	protected List<StyleToken> styleContributors()
	{
		final List<StyleToken> styleContributors = new ArrayList<StyleToken>();
		styleContributors.add(this.width);
		styleContributors.add(this.height);
		return styleContributors;
	}

	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();

		// initialize lazily
		if (domId == null) {
			// assign the markup id
			String id = capitalize(getMarkupId());
			domId = "dom" + id;
			varId = "var" + id;
			activeWidgetsId = "aw" + id;
		}
	}


	public Grid setWidth(int width) {
		this.width.setValue(new Integer(width).toString(), Unit.px);
		return this;
	}


	public Grid setHeight(int height) {
		this.height.setValue(new Integer(height).toString(), Unit.px);
		return this;
	}


	public Grid setSelectorVisible(boolean visible) {
		this.selectorVisible.setValue(new Boolean(visible).toString());
		return this;
	}


	public Grid setRowCount(int count) {
		this.rowCount.setValue(new Integer(count).toString());
		return this;
	}

}
