package wicket.contrib.activewidgets.markup.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.template.PackagedTextTemplate;

import wicket.contrib.activewidgets.AWHeaderContributor;

public class Grid extends Panel  implements IHeaderContributor {

	static final int DEFAULT_PROIRITY = 1000;
	static final int JS_DEBUT = DEFAULT_PROIRITY / 2;
	static final int JS_MITTELSPIEL = DEFAULT_PROIRITY;
	static final int JS_ENDSPEIL = DEFAULT_PROIRITY * 2;
	static final int JS_MATT = JS_ENDSPEIL * 2;
	
	interface IToken {
		String getToken();
		String getTokenName();
		void setValue(String value);
		//Object getDefaultValue();
	}
	
	abstract class Token implements IToken, Comparable<Grid.Token> {

		/*** serialization 	 */
		private static final long serialVersionUID = 1L;
		protected String value;
		protected int priority;
		
		public Token() {
			this.priority = DEFAULT_PROIRITY;
		}
		public Token(int priority) {
			this.priority = priority;
		}
		public Token(int priorrity, String value) {
			this.priority = priorrity;
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public int compareTo(Grid.Token o) {
			return this.priority - o.priority;
		}
		
	}
	
	abstract class JavascriptToken extends Token {
		public JavascriptToken(int priority) {
			super(priority);
		}

		public JavascriptToken(int priority, String value) {
			super(priority);
			this.value = value;
		}

		/*** serialization 	 */
		private static final long serialVersionUID = 1L;
		
		public String getToken() {
			return 	"\n" + varId + "." + getTokenName() + "(" + getValue() + ");";
		}
	}
	
	abstract class DocumentWrite extends JavascriptToken {
		public DocumentWrite(int priority, String value) {
			super(priority);
			this.value = value;
		}

		/*** serialization 	 */
		private static final long serialVersionUID = 1L;
		
		public String getToken() {
			return 	"\n" + "document.write(" + getValue() + ");";
		}

		public String getTokenName() {
			return null;
		}
	}
	
	abstract class ConstructorToken extends JavascriptToken {
		public ConstructorToken(int priority, String activeWidgetsClass) {
			super(priority);
			this.value = activeWidgetsClass;
		}
		/*** serialization 	 */
		private static final long serialVersionUID = 1L;
		public String getToken() {
			return 	"\n" + "var " + varId + " = new " + getValue() + ";";
		}
		public String getTokenName() {
			return null;
		}
	}
	
	abstract class StyleToken extends Token {

		/*** serialization 	 */
		private static final long serialVersionUID = 1L;

		public String getToken() {
			return 	"\n\t" + "#" + activeWidgetsId + " {" + getTokenName() + ":" + value + "}";
		}
		
	}
	abstract class StyleTokenPx extends StyleToken {

		/*** serialization 	 */
		private static final long serialVersionUID = 1L;

		public String getToken() {
			return 	"\n" + "#" + activeWidgetsId + " {" + getTokenName() + ":" + value + "px}";
		}
		void setPxValue(int pxValue) {
			this.value = new Integer(pxValue).toString();
		}
		
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The DOM id of the element that hosts the javascript component.
	 */
	private String domId;

	/**
	 * The JavaScript variable name of the grid component.
	 */
	private String varId;

	

	/**
	 * active widgets grid ID
	 */
	private String activeWidgetsId;
	
	private GirdElement gridElement;
	
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
	/**
	 * The container/ receiver of the javascript component.
	 */
	final class GirdElement extends FormComponent {

		private static final long serialVersionUID = 1L;

		public GirdElement(String id, IModel model) {
			super(id, model);
		}

		public GirdElement(String id) {
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public Object getObject()
				{
					return domId;
				}
			}));
		}

	}


	private void constructorInit(String id) {
		
		add(new AWHeaderContributor());
		add(gridElement = new GirdElement("gridContainer"));
		
		Label style = new Label("style", new AbstractReadOnlyModel()
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see wicket.model.IModel#getObject(wicket.Component)
			 */
			@Override
			public Object getObject()
			{
				StringBuffer result = new StringBuffer();
				result.append(styleInit());
				return result.toString();
			}
		});
		style.setEscapeModelStrings(false);
		gridElement.add(style);

		Label javascript = new Label("javascript", new AbstractReadOnlyModel()
		{
			private static final long serialVersionUID = 1L;
			@Override
			public Object getObject()
			{
				StringBuffer result = new StringBuffer();
				result.append(javascriptInit());
				return result.toString();
			}
		});
		javascript.setEscapeModelStrings(false);
		gridElement.add(javascript);
		
	}

	
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
	private String javascriptInit()
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
	private String styleInit()
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
			domId = "AW" + id;
			varId = "var" + domId;
			activeWidgetsId = "_" + domId;
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
