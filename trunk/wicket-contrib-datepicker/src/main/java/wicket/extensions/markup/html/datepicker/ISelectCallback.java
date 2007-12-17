package wicket.extensions.markup.html.datepicker;

import java.io.Serializable;

import org.apache.wicket.Component;

/**
 * Interface which tells what to do when the user has selected something in
 * the date picker.
 * 
 * @author Frank Bille Jensen
 */
public interface ISelectCallback extends Serializable
{
	/**
	 * Bind the component if needed.
	 * 
	 * @param component
	 *            The component to bind.
	 */
	void bind(Component component);

	/**
	 * Return the javascript which are being invoked when the user clicks on
	 * something in the date picker.
	 * 
	 * @return The javascript which are being invoked when the user clicks
	 *         on something in the date picker.
	 */
	CharSequence handleCallback();
}