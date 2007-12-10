package wicket.contrib.activewidgets.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.template.PackagedTextTemplate;

import wicket.contrib.activewidgets.ActiveWidgetsComponent;

public class Grid extends ActiveWidgetsComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StyleTokenPx width = new StyleTokenPx() {

		public String getTokenName() {
			return "width";
		}
		
	};

	private StyleTokenPx height = new StyleTokenPx() {

		public String getTokenName() {
			return "height";
		}
		
	};


	
	private JavascriptToken selectorVisible = new JavascriptToken(JS_MITTELSPIEL) {

		public String getTokenName() {
			return "setSelectorVisible";
		}
		
	};

	private List<Token> javascriptContributors = new ArrayList<Token>();
	private List<StyleToken> styleContributors = new ArrayList<StyleToken>();
	private JavascriptToken rowCount = new JavascriptToken(JS_MITTELSPIEL) {
		public String getTokenName() {
			return "setRowCount";
		}
	};
	
	
	public Grid(String id, IModel model) {
		super(id, model);
		constructorInit(id);
	}

	public Grid(String id) {
		super(id);
		constructorInit(id);
	}

	/**
	 * 
	 * @return the initilization javascript
	 */
	protected String javascriptInit()
	{
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

		StringBuffer buffer = new StringBuffer();
		Collections.sort(this.javascriptContributors);
		for (Token token: javascriptContributors) {
			buffer.append(token.getToken());
		}
		buffer.append('\n');
		return buffer.toString();
	}

	/**
	 * 
	 * @return the initilization javascript
	 */
	protected String styleInit()
	{
		StringBuffer result = new StringBuffer();
		Collections.sort(this.styleContributors);
		for (StyleToken token: this.styleContributors) {
			result.append(token.getToken());
		}
		return result.toString();
	}

	/**
	 * @see wicket.Component#onAttach()
	 */
	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();

		// initialize lazily
		if (domId == null) {
			// assign the markup id
			String id = getMarkupId();
			domId = "dom" + id;
			varId = "var" + id;
			activeWidgetsId = "aw" + id;
		}
	}


	public void renderHead(IHeaderResponse response) {
		// TODO Auto-generated method stub
		
	}
	
	
	public Grid setWidth(int width) {
		this.width.setPxValue(width);
		this.styleContributors.add(this.width);
		return this;
	}


	public Grid setHeight(int height) {
		this.height.setPxValue(height);
		this.styleContributors.add(this.height);
		return this;
	}


	public Grid setSelectorVisible(boolean visible) {
		this.selectorVisible.setValue(new Boolean(visible).toString());
		this.javascriptContributors.add(selectorVisible);
		return this;
	}


	public Grid setRowCount(int count) {
		this.rowCount.setValue(new Integer(count).toString());
		this.javascriptContributors.add(rowCount);
		return this;
	}

}
