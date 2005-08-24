package wicket.contrib.ajax.sandbox.autocomplete;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import wicket.Application;
import wicket.AttributeModifier;
import wicket.IInitializer;
import wicket.contrib.ajax.sandbox.autocomplete.misc.IdPathAttributeModifier;
import wicket.contrib.ajax.sandbox.autocomplete.misc.PrependAttributeModifier;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.HtmlHeaderContainer;
import wicket.markup.html.PackageResourceReference;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.border.Border;
import wicket.markup.html.form.TextField;
import wicket.markup.html.image.Image;
import wicket.model.Model;

public class AutoCompleteTextFieldBorder extends Border {

	/** thread local for head contributions. */
	private static final ThreadLocal headContribHolder = new ThreadLocal();
	/** we just need one simple indicator object to put in our thread locals. */
	private static final Object dummy = new Object();
	
	public static class Initializer implements IInitializer {
		public void init(Application application) {

			// TODO do I have to use the PackageResource.bind static method, or can I bind the static instances themselves ?
			//PackageResource.bind(application, AutoCompleteTextFieldBorder.class, "indicator.gif");
			
			DEFAULT_INDICATOR.bind(application);
			PROTOTYPE.bind(application);
			SCRIPTACULOUS_UTIL.bind(application);
			SCRIPTACULOUS_CONTROLS.bind(application);
			SCRIPTACULOUS_EFFECTS.bind(application);
		}
	}
	
	
	private static final PackageResourceReference DEFAULT_INDICATOR = new PackageResourceReference(AutoCompleteTextFieldBorder.class, "indicator.gif");
	
	private static final PackageResourceReference PROTOTYPE = new PackageResourceReference(AutoCompleteTextFieldBorder.class, "prototype-1.4.0_pre2.js");
	private static final PackageResourceReference SCRIPTACULOUS_UTIL = new PackageResourceReference(AutoCompleteTextFieldBorder.class, "scriptaculous/util.js");
	private static final PackageResourceReference SCRIPTACULOUS_CONTROLS = new PackageResourceReference(AutoCompleteTextFieldBorder.class, "scriptaculous/controls.js");
	private static final PackageResourceReference SCRIPTACULOUS_EFFECTS = new PackageResourceReference(AutoCompleteTextFieldBorder.class, "scriptaculous/effects.js");
	
	public AutoCompleteTextFieldBorder(String id, Class responsePage, TextField input) {
		super(id);
		setRenderBodyOnly(true);

		// TODO suport using a response page instance to avoid having to create new instances each time
		this.responsePage = responsePage;

		this.input = input;
		
		// TODO create a 
		this.input.add(new PrependAttributeModifier("style", true, new Model("border:0px;")));
		
		// prevent the normal html autocomplete popup from appearing
		this.input.add(new AttributeModifier("autocomplete", true, new Model("false")));
		
		// TODO : what happens if the user specifies their own ID attribute for CSS / JavaScript access - may need to use a custom wicket:path attribute, and provide some JS to locate the control
		IdPathAttributeModifier.bind(input);
		add(input);
		
		// TODO : make the indicator configurable
		Image image = new Image("indicator", DEFAULT_INDICATOR);
		IdPathAttributeModifier.bind(image);
		add(image);
		this.indicator = image;
		
		// TODO : make the response page parameter name configurable  
		this.paramName = "input";
		
		add(new JsInit());
		
	}
	
	/**
	 * @see wicket.markup.html.IHeaderContributor#printHead(wicket.markup.html.HtmlHeaderContainer)
	 */
	public final void renderHead(HtmlHeaderContainer container)
	{
		// TODO ensure that these are only added once per page - this could probably be moved into WebMarkupContainer to do this properly, we need a javascript package manager
		if (headContribHolder.get() == null)
		{
			headContribHolder.set(dummy);
			printHeadInitContribution(container);
		}
		super.renderHead(container);
	}
	
	protected void onRender() {
		super.onRender();
		headContribHolder.set(null);
	}
	
	public void printHeadInitContribution(HtmlHeaderContainer container) {
		// TODO ensure that other components don't add these same javascript includes
		addJsReference(container, PROTOTYPE);
		addJsReference(container, SCRIPTACULOUS_UTIL);
		addJsReference(container, SCRIPTACULOUS_EFFECTS);
		addJsReference(container, SCRIPTACULOUS_CONTROLS);
	}
	
	protected TextField input;
	protected Image indicator;
	protected String paramName;
	protected Class responsePage;
	
	class JsInit extends WebMarkupContainer {

		public JsInit() {
			super("jsinit");
			setRenderBodyOnly(true);
		}


		protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
			super.onComponentTagBody(markupStream, openTag);
	
			Properties options = new Properties();
			if (indicator != null) {
				// TODO escape quoted value
				options.put("indicator", "'" + indicator.getPath() + "'");
			}
			if (paramName != null) {
				// TODO escape quoted value
				options.put("paramName", "'" + paramName + "'");
			}
			
			String updateId = input.getPath() + "_update";
	
			StringBuffer scriptBlock = new StringBuffer();
			// the actual autocomplete dropdown div container
			scriptBlock.append("<div class='auto_complete' id='" + updateId + "'></div>");
			
			// call the Ajax.Autocomplete javascript control 
			scriptBlock.append("<script type='text/javascript'>");
			scriptBlock.append("new Ajax.Autocompleter('" + input.getPath() + "', '" + updateId + "', '" + getPage().urlFor(null, responsePage, null) + "', " + jsHash(options) + ");");
			scriptBlock.append("</script>\n");
			getResponse().write(scriptBlock.toString());
		}
	}
	
	/**
	 * TODO candidate for a JavaScript util class - requires a JavaScriptEncode function
	 */
	private String jsHash(Properties options) {
		StringBuffer hash = new StringBuffer(10);
		hash.append("{");
		for (Iterator iter = options.entrySet().iterator(); iter.hasNext(); ) {
			Map.Entry entry = (Map.Entry) iter.next();
			
			// TODO jsEncode key
			hash.append(entry.getKey());

			hash.append(":");

			hash.append(entry.getValue());
			
			if (iter.hasNext()) {
				hash.append(",");
			}
			
		}
		hash.append("}");
		return hash.toString();
	}
	

    private void addJsReference(HtmlHeaderContainer container, PackageResourceReference ref) {
		container.getResponse().write("<script type='text/javascript' src='" + container.getPage().urlFor(ref.getPath()) + "'></script>\n");
	}

}
