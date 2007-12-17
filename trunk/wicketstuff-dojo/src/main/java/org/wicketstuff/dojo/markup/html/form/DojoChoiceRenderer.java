package org.wicketstuff.dojo.markup.html.form;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

/**
 * The default choice renderer used in {@link DropDownChoice} is not
 * convenient to {@link DojoDropDownChoice} because it render Id value using 
 * the index on the list. But Dojo send input as value for the drop down.
 * <p>
 * This Choice rendere will overwrite the default one to render Id Value 
 * as Dojo will interpret it
 * </p>
 * @author Vincent Demay
 *
 */
public class DojoChoiceRenderer implements IChoiceRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object getDisplayValue(Object object) {
		if (object == null)
		{
			return "";
		}
		return object;
	}

	public String getIdValue(Object object, int index) {
		if (object == null)
		{
			return "";
		}
		return object.toString();
	}
	
}
