/**
 * 
 */
package org.wicketstuff.dojo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Abstract behavior implementation that deals with dojo widgets.
 * 
 * @author B. Molenkamp
 * @version SVN: $Id$
 */
public abstract class AbstractDojoWidgetBehavior extends AbstractRequireDojoBehavior {

	/* (non-Javadoc)
	 * @see org.wicketstuff.dojo.AbstractRequireDojoBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		String markupId = getComponent().getMarkupId();
		WidgetProperties props = getWidgetProperties();
		String arrayString = props.convertToJavaScriptArray();

		IRequestTarget target = RequestCycle.get().getRequestTarget();
		if(!(target instanceof AjaxRequestTarget)){
			response.renderJavascript("dojo.event.connect(dojo, \"loaded\", function() {" +
					"dojo.widget.createWidget('" + getWidgetType() + "', " + arrayString + ", dojo.byId('" + markupId + "'))" +
					"});\n", 
					markupId + "onLoad");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.wicketstuff.dojo.AbstractRequireDojoBehavior#onComponentReRendered(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	public void onComponentReRendered(AjaxRequestTarget ajaxTarget) {
		super.onComponentReRendered(ajaxTarget);
		
		String markupId = getComponent().getMarkupId();
		
		
		//dojo.widget.createWidget("Editor2", {}, dojo.byId("editorContent"));
		WidgetProperties props = getWidgetProperties();
		String arrayString = props.convertToJavaScriptArray();
		ajaxTarget.appendJavascript("dojo.widget.createWidget('" + getWidgetType() + "', " + arrayString + ", dojo.byId('" + markupId + "'));\n");
	}
	
	/**
	 * Returns the widget type.
	 * @return
	 */
	protected abstract String getWidgetType();
	
	/**
	 * Return the properties string that is used when creating the widget.
	 * @return
	 */
	protected WidgetProperties getWidgetProperties() {
		// by default return an empty map.
		return new WidgetProperties();
	}
	
	/**
	 * A class to hold widget properties.
	 */
	public class WidgetProperties {
		
		private Map<String, String> properties;
		
		public WidgetProperties() {
			this.properties = new HashMap<String, String>();
		}
		
		/**
		 * Adds a boolean property.
		 * @param name
		 * @param value
		 */
		public void addProperty(String name, boolean value) {
			this.properties.put(name, Boolean.toString(value));
		}
		
		/**
		 * Adds an integer property
		 * @param name
		 * @param value
		 */
		public void addProperty(String name, int value) {
			this.properties.put(name, Integer.toString(value));
		}
		
		/**
		 * Adds a string property (encloses the string in qoutes).
		 * @param name
		 * @param value
		 */
		public void addProperty(String name, String value) {
			value = value.replace("\"", "\\\"");	// escape any double qoute.
			this.properties.put(name, "\"" + value + "\"");
		}
		
		/**
		 * Adds a resource reference property. The resource is looked up using
		 * Dojo's dojo.uri.dojoUri() function. Relative paths are handled.
		 * 
		 * @param name
		 * @param reference
		 */
		public void addProperty(String name, ResourceReference reference) {
			RequestCycle cycle = RequestCycle.get();
			
			ResourceReference dojo = AbstractDojoWidgetBehavior.this.getDojoResourceReference();
			String dojoUrl = cycle.urlFor(dojo).toString();
			dojoUrl = dojoUrl.substring(0, dojoUrl.lastIndexOf("/") + 1);
			
			CharSequence url = cycle.urlFor(reference);
			String relativeUrl = dojoUrl.replaceAll(".[^/]*/", "../") + url;
			this.properties.put(name, "dojo.uri.dojoUri(\"" + relativeUrl + "\")");
		}
		
		/**
		 * Adds a raw property. The object's toString() is called and added
		 * without any further conversion.
		 * @param name
		 * @param value
		 */
		public void addRawProperty(String name, Object value) {
			this.properties.put(name, value.toString());
		}
		
		/**
		 * Convert the properties to a valid javascript array.
		 * @return
		 */
		public String convertToJavaScriptArray() {
			// convert the properties to string
			StringBuffer propertyString = new StringBuffer("{");
			Iterator<String> i = this.properties.keySet().iterator();
			while (i.hasNext()) {
				String propertyName = i.next();
				Object property = this.properties.get(propertyName);
				
				propertyString.append(propertyName + ": " + property);
				
				// if there are more properties, separate them by a comma
				if (i.hasNext()) {
					propertyString.append(", ");
				}
			}
			
			// close the property string.
			propertyString.append("}");
			
			return propertyString.toString();
		}
	}
}
