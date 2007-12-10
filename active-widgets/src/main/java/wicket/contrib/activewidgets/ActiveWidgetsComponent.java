package wicket.contrib.activewidgets;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class ActiveWidgetsComponent extends Panel {

	public ActiveWidgetsComponent(String id, IModel model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}

	public ActiveWidgetsComponent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

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
	
}
