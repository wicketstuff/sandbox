/**
 * 
 */
package org.wicketstuff.picnik.api;

/**
 * Export settings.
 * <p>
 * Created 29.03.2008 23:42:28
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class ExportSettings extends AbstractSettings {

	// _export: a URL to post the result to (see http://www.picnik.com/info/api/reference/_export)
	private String target;

	// _export_agent: source of export data 
	// (see http://www.picnik.com/info/api/reference/_export_agent)
	private Agent agent;

	// _export_field: name of the form field containing export data (see
	// http://www.picnik.com/info/api/reference/_export_field)
	private String field;

	// _export_method: GET or POST (see http://www.picnik.com/info/api/reference/_export_method)
	private Method method;

	// _export_title: text to use for the export button (see
	// http://www.picnik.com/info/api/reference/_export_title)
	private String title;

	// _imageid: the id of the original image (see
	// http://www.picnik.com/info/api/reference/_imageid)
	private String imageid;

	// _original_thumb: a URL to a thumbnail of the original image (see
	// http://www.picnik.com/info/api/reference/_original_thumb)
	private String originalThumb;

	// _redirect: A URL to redirect the browser to after export (see
	// http://www.picnik.com/info/api/reference/_redirect)
	private String redirect;

	// _replace: controls whether to ask the user before overwriting (see
	// http://www.picnik.com/info/api/reference/_replace)
	private Replace replace;

	public enum Agent {
		/**
		 * 
		 */
		picnik("picnik.com"), 
		
		browser("browser");

		private final String name;

		Agent(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public enum Method {
		GET, POST
	}

	public enum Replace {
		ask, confirm, yes, no
	}

	@Override
	public ExportSettings fillParameters() {
		addParam("_export", getTarget());
		addParam("_export_agent", getAgent());
		addParam("_export_field", getField());
		addParam("_export_method", getMethod());
		addParam("_export_title", getTitle());
		addParam("_imageid", getImageid());
		addParam("_original_thumb", getOriginalThumb());
		addParam("_redirect", getRedirect());
		return this;
	}

	/**
	 * Get the target.
	 * @return Returns the target.
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Set the target. _export: a URL to post the result to
	 * @see http://www.picnik.com/info/api/reference/_export
	 * @param target The target to set.
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Get the agent.
	 * @return Returns the agent.
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * Set the agent. _export_agent: source of export data
	 * @see http://www.picnik.com/info/api/reference/_export_agent
	 * @param agent The agent to set.
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	/**
	 * Get the field.
	 * @return Returns the field.
	 */
	public String getField() {
		return field;
	}

	/**
	 * Set the field. _export_field: name of the form field containing export data.
	 * @see http://www.picnik.com/info/api/reference/_export_field
	 * @param field The field to set.
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Get the method.
	 * @return Returns the method.
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * Set the method. _export_method: GET or POST
	 * @see http://www.picnik.com/info/api/reference/_export_method
	 * @param method The method to set.
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	/**
	 * Get the title.
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title. _export_title: text to use for the export button
	 * @see http://www.picnik.com/info/api/reference/_export_title
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the imageid.
	 * @return Returns the imageid.
	 */
	public String getImageid() {
		return imageid;
	}

	/**
	 * Set the imageid. _imageid: the id of the original image
	 * @see http://www.picnik.com/info/api/reference/_imageid
	 * @param imageid The imageid to set.
	 */
	public void setImageid(String imageid) {
		this.imageid = imageid;
	}

	/**
	 * Get the originalThumb.
	 * @return Returns the originalThumb.
	 */
	public String getOriginalThumb() {
		return originalThumb;
	}

	/**
	 * Set the originalThumb. _original_thumb: a URL to a thumbnail of the original image
	 * @see http://www.picnik.com/info/api/reference/_original_thumb
	 * @param originalThumb The originalThumb to set.
	 */
	public void setOriginalThumb(String originalThumb) {
		this.originalThumb = originalThumb;
	}

	/**
	 * Get the redirect.
	 * @return Returns the redirect.
	 */
	public String getRedirect() {
		return redirect;
	}

	/**
	 * Set the redirect. _redirect: A URL to redirect the browser to after export
	 * @see http://www.picnik.com/info/api/reference/_redirect
	 * @param redirect The redirect to set.
	 */
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	/**
	 * Get the replace.
	 * @return Returns the replace.
	 */
	public Replace getReplace() {
		return replace;
	}

	/**
	 * Set the replace. _replace: controls whether to ask the user before overwriting
	 * @see http://www.picnik.com/info/api/reference/_replace
	 * @param replace The replace to set.
	 */
	public void setReplace(Replace replace) {
		this.replace = replace;
	}

}
