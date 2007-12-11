package wicket.contrib.activewidgets;

import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.wicket.model.Model;

@SuppressWarnings("serial")
abstract public class ActiveWidgetsComponent extends Panel {


	protected class Refresh extends JavascriptToken {

		public Refresh(int priority) {
			super(priority);
		}
		public String getToken() {
			return activeWidgetsId + ".refresh();";
		}
		public String getTokenName() {
			return null;
		}
	}

	private ActiveWidgetsConfiguration.CreateMode createMode = ActiveWidgetsConfiguration.getDefaultCreateMode();

	public ActiveWidgetsComponent(String id, IModel model) {
		super(id, model);
		constructorInit();
	}

	public ActiveWidgetsComponent(String id) {
		super(id);
		constructorInit();
	}

	private transient static AbstractHeaderContributor headerContributor = 
		new AbstractHeaderContributor() {

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
		
		
	};
		
	private void constructorInit() {
		
		add (headerContributor);
		add(markupElement = new MarkupElement("container"));
		
		final Label style = new Label("style", new AbstractReadOnlyModel()
		{
			@Override
			public Object getObject()
			{
				return styleInit();
			}
		});
		style.setEscapeModelStrings(false);
		add(style);

		Label javascript = new Label("javascript", new AbstractReadOnlyModel()
		{
			private static final long serialVersionUID = 1L;
			@Override
			public Object getObject()
			{
				return javascriptInit();
			}
		});
		javascript.setEscapeModelStrings(false);
		add(javascript);
		
	}

	
	abstract protected List<StyleToken> styleContributors();
	abstract protected List<Token> javascriptContributors();
	
	private final String styleInit() {
		StringBuffer buffer = new StringBuffer();
		StringBuffer styles = new StringBuffer();
		List<StyleToken> styleContributors = styleContributors();
		Collections.sort(styleContributors);
		for (StyleToken token: styleContributors) {
			if (token instanceof StyleStyleToken) {
				styles.append(token.getToken());
			} else {
				buffer.append('\n').append('\t').append(token.getToken());
			}
		}
		buffer.append('\n').append('\t').append('#').append(activeWidgetsId).append(' ')
			.append('{').append(styles).append('}');
		buffer.append('\n');
		return buffer.toString();
		
	}
	
	private final String javascriptInit() {
		StringBuffer buffer = new StringBuffer();
		List<Token> javascriptContributors = javascriptContributors();

		if (this.createMode == ActiveWidgetsConfiguration.CreateMode.DOCUMENT_WRITE) {
			javascriptContributors.add(new DocumentWrite(JS_MATT, activeWidgetsId) {});
		} else {
			javascriptContributors.add(new Refresh(JS_MATT));
		}
		Collections.sort(javascriptContributors);
		for (Token token: javascriptContributors) {
			buffer.append('\n').append('\t').append(token.getToken());
		}

		buffer.append('\n');
		return buffer.toString();
		
	}
	/**
	 * The container/ receiver of the ActiveWidgets component.
	 */
	final class MarkupElement extends FormComponent {

		public MarkupElement(String id, IModel model) {
			super(id, model);
		}

		public MarkupElement(String id) {
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel()
			{
				@Override
				public Object getObject()
				{
					return activeWidgetsId;
				}
			}));
		}

	}

	private MarkupElement markupElement;
	
	
	protected static final int DEFAULT_PROIRITY = 1000;
	protected static final int JS_DEBUT = DEFAULT_PROIRITY / 2;
	protected static final int JS_MITTELSPIEL = DEFAULT_PROIRITY;
	protected static final int JS_ENDSPEIL = DEFAULT_PROIRITY * 2;
	protected static final int JS_MATT = JS_ENDSPEIL * 2;
	

	/**
	 * The JavaScript variable name of the grid component.
	 */
//	protected String varId;

	

	/**
	 * The DOM id of the element that hosts the javascript component.
	 */
//	protected String domId; 
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
			return 	"var " + activeWidgetsId + " = new " + getValue() + ";";
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
			return 	"document.write(" + getValue() + ");";
		}
	
		public String getTokenName() {
			return null;
		}
	}
	
	interface IToken {
		String getToken();
		String getTokenName();
		void setValue(String value);
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
			return 	activeWidgetsId + "." + getTokenName() + "(" + getValue() + ");";
		}
	}
	
	protected abstract class StyleToken extends Token {
	
		/*** serialization 	 */
		private static final long serialVersionUID = 1L;
	
		public StyleToken() {
			super();
		}
		public StyleToken(String value, Unit unit) {
			super(value, unit);
		}
		public String getToken() {
			return 	"\t#" + activeWidgetsId + " {" + getTokenName() + ":" + value + "}";
		}
		
	}
	
	protected enum Unit {
		px, procent, blank;

		@Override
		public final String toString() {
			if (this == procent) {
				return "%";
			} else if (this == blank) {
				return "";
			} else {
				return super.toString();
			}
		}
	}
	protected abstract class StyleStyleToken extends StyleToken {
		
		/*** serialization 	 */
		private static final long serialVersionUID = 1L;
		public StyleStyleToken(String value) {
			this.value = value;
		}
		public StyleStyleToken(String value, Unit unit) {
			super(value, unit);
		}
		public String getToken() {
			return 	 getTokenName() + ": " + value + unit.toString() + ";";
		}
		
	}
	
	
	protected abstract class Token implements IToken
		, Comparable<ActiveWidgetsComponent.Token>
	{
	
		/*** serialization 	 */
		private static final long serialVersionUID = 1L;
		protected String value;
		protected int priority;
		protected Unit unit;
		
		
		public Token() {
			this.priority = DEFAULT_PROIRITY;
		}
		public Token(int priority) {
			this.priority = priority;
		}
		public Token(int priorrity, String value, Unit unit) {
			this(priorrity);
			this.value = value;
			this.unit = unit;
		}
		
		public Token(String value) {
			this(DEFAULT_PROIRITY, value, Unit.blank);
		}
		
		public Token(String value, Unit unit) {
			this(DEFAULT_PROIRITY, value, unit);
		}
		public Token(int priority, String value) {
			this(priority, value, Unit.blank);
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public void setValue(String value, Unit unit) {
			this.value = value;
			this.unit = unit;
		}
		
		public int compareTo(ActiveWidgetsComponent.Token o) {
			return this.priority - o.priority;
		}
		
	}
	
	/**
	 * TODO move to util class
	 * @param lowCase
	 * @return
	 */
	protected String capitalize(String lowCase) {
		if (lowCase != null) {
			String first = lowCase.substring(0, 1).toUpperCase();
			return first + lowCase.substring(1);
		}
		return null;
	}

	
}
