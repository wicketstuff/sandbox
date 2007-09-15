package wicketstuff.crud;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

/**
 * Represents a {@link Component} used as property editor. Editors are used in
 * the edit screen as well as in the filter toolbar
 * 
 * @author igor.vaynberg
 * 
 */
public interface Editor
{
	/**
	 * Sets the required flag
	 * 
	 * @param required
	 */
	void setRequired(boolean required);

	/**
	 * 
	 * @return true if value is required, false otherwise
	 */
	boolean isRequired();


	/**
	 * Adds a validator to the editor
	 * 
	 * @param validator
	 */
	void add(IValidator validator);

	/**
	 * Sets editor's label
	 * 
	 * @param label
	 */
	void setLabel(IModel label);

	/**
	 * 
	 * @return editor's label
	 */
	IModel getLabel();
}
