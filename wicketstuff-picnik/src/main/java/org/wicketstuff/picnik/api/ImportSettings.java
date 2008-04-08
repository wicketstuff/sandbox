/**
 * 
 */
package org.wicketstuff.picnik.api;

import org.apache.wicket.markup.html.form.FormComponent;

/**
 * Importing settings.
 * <p>
 * Created 29.03.2008 23:23:06
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class ImportSettings extends AbstractSettings {

	// _import: http://www.picnik.com/info/api/reference/_import
	private String importSource;

	/**
	 * In a Wicket environment, only load makes really sense.
	 * @see http://www.picnik.com/info/api/reference/_returntype
	 */
	public enum ReturnType {
		load, text
	}

	// _returntype: http://www.picnik.com/info/api/reference/_returntype
	private ReturnType returnType;

	// _title: http://www.picnik.com/info/api/reference/_title
	private String title;


	@Override
	public ImportSettings fillParameters() {
		addParam("_import", getImportSource());
		addParam("_returntype", getReturnType());
		addParam("_title", getTitle());
		return this;
	}
	
	/**
	 * Get the importSource.
	 * @see http://www.picnik.com/info/api/reference/_import
	 * @return Returns the _import.
	 */
	public String getImportSource() {
		return importSource;
	}

	/**
	 * Set the importSource to an image URL to be imported.
	 * @see http://www.picnik.com/info/api/reference/_import
	 * @param importSource The importSource to set.
	 */
	public void setImportSource(String importSource) {
		this.importSource = importSource;
	}
	
	/**
	 * Set the _import to a form component. It must already be in the
	 * form which will be submitted to Picnik, and will contain
	 * either the image URL, or is a FileUploadField sending the image data.
	 * @param formComponent
	 * @see FormComponent#getInputName()
	 */
	public void setImportSource(FormComponent formComponent) {
		setImportSource(formComponent.getInputName());
	}

	/**
	 * Get the returnType.
	 * @return Returns the returnType.
	 */
	public ReturnType getReturnType() {
		return returnType;
	}

	/**
	 * Set the returnType.
	 * @see http://www.picnik.com/info/api/reference/_returntype
	 * @param returnType The returnType to set.
	 */
	public void setReturnType(ReturnType returnType) {
		this.returnType = returnType;
	}

	/**
	 * Get the title.
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title.
	 * @see http://www.picnik.com/info/api/reference/_title
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
