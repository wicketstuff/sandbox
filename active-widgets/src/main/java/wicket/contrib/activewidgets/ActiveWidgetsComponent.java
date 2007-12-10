package wicket.contrib.activewidgets;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractHeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

abstract public class ActiveWidgetsComponent extends Panel  implements IHeaderContributor {

	public ActiveWidgetsComponent(String id, IModel model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}

	public ActiveWidgetsComponent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	protected void constructorInit(String id) {
		
		add (new AbstractHeaderContributor() {

			@Override
			public IHeaderContributor[] getHeaderContributors() {
				List<IHeaderContributor> contributors = new ArrayList<IHeaderContributor>();
				IHeaderContributor mainJs = new IHeaderContributor () {

					ResourceReference mainJs = new ResourceReference(ActiveWidgetsComponent.class
							, ActiveWidgetsConfiguration.AW_LIB_HOME_PATH + "/lib/aw.js");
					public void renderHead(IHeaderResponse response) {
						response.renderJavascriptReference(mainJs);
					}
					
				};
				
				contributors.add(mainJs);

				IHeaderContributor mainCss = new IHeaderContributor () {
					ResourceReference mainCss = new ResourceReference(ActiveWidgetsComponent.class, 
							ActiveWidgetsConfiguration.AW_LIB_HOME_PATH + "/styles/xp/aw.css");
					public void renderHead(IHeaderResponse response) {
						response.renderCSSReference(mainCss);
					}
					
				};
				contributors.add(mainCss);

		        return contributors.toArray(new IHeaderContributor[]{});
			}
			
			
		}
		);
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

	
	abstract protected String styleInit();
	abstract protected String javascriptInit();
	
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

	private GirdElement gridElement;
	
	
	protected static final int DEFAULT_PROIRITY = 1000;
	protected static final int JS_DEBUT = DEFAULT_PROIRITY / 2;
	protected static final int JS_MITTELSPIEL = DEFAULT_PROIRITY;
	protected static final int JS_ENDSPEIL = DEFAULT_PROIRITY * 2;
	protected static final int JS_MATT = JS_ENDSPEIL * 2;
	
	/**
	 * The DOM id of the element that hosts the javascript component.
	 */
	protected String domId;

	/**
	 * The JavaScript variable name of the grid component.
	 */
	protected String varId;

	

	/**
	 * active widgets grid ID
	 */
	protected String activeWidgetsId;
	


	protected abstract class ConstructorToken extends JavascriptToken {
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
	
	protected abstract class DocumentWrite extends JavascriptToken {
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
	
	interface IToken {
		String getToken();
		String getTokenName();
		void setValue(String value);
		//Object getDefaultValue();
	}
	
	protected abstract class JavascriptToken extends Token {
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
	
	protected abstract class StyleToken extends Token {
	
		/*** serialization 	 */
		private static final long serialVersionUID = 1L;
	
		public String getToken() {
			return 	"\n\t" + "#" + activeWidgetsId + " {" + getTokenName() + ":" + value + "}";
		}
		
	}
	
	protected abstract class StyleTokenPx extends StyleToken {
	
		/*** serialization 	 */
		private static final long serialVersionUID = 1L;
	
		public String getToken() {
			return 	"\n" + "#" + activeWidgetsId + " {" + getTokenName() + ":" + value + "px}";
		}
		public void setPxValue(int pxValue) {
			this.value = new Integer(pxValue).toString();
		}
		
	}
	
	protected abstract class Token implements IToken, Comparable<ActiveWidgetsComponent.Token> {
	
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
		public int compareTo(ActiveWidgetsComponent.Token o) {
			return this.priority - o.priority;
		}
		
	}
	
	
	@SuppressWarnings("serial")
	public IHeaderContributor[] getHeaderContributors() {
		List<IHeaderContributor> contributors = new ArrayList<IHeaderContributor>();
		IHeaderContributor mainJs = new IHeaderContributor() {

			ResourceReference mainJs = new ResourceReference(this.getClass()
					, ActiveWidgetsConfiguration.AW_LIB_HOME_PATH + "/lib/aw.js");
			public void renderHead(IHeaderResponse response) {
				response.renderJavascriptReference(mainJs);
			}
			
		};
		
		contributors.add(mainJs);

		IHeaderContributor mainCss = new IHeaderContributor () {
			ResourceReference mainCss = new ResourceReference(this.getClass(), 
					ActiveWidgetsConfiguration.AW_LIB_HOME_PATH + "/styles/xp/aw.css");
			public void renderHead(IHeaderResponse response) {
				response.renderCSSReference(mainCss);
			}
			
		};
		contributors.add(mainCss);

        return contributors.toArray(new IHeaderContributor[]{});
	}

	public void renderHead(IHeaderResponse response) {
		// TODO Auto-generated method stub
		
	}
	
	
}
