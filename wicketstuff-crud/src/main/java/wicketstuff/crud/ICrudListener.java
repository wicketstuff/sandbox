package wicketstuff.crud;

import org.apache.wicket.IClusterable;
import org.apache.wicket.model.IModel;

/**
 * Listener used by views to communicate with the crud panel
 * 
 * @author igor.vaynberg
 * 
 */
public interface ICrudListener extends IClusterable
{
	/**
	 * Actvate the view screen
	 * 
	 * @param selected
	 */
	void onView(IModel selected);

	/**
	 * Activate the create screen
	 */
	void onCreate();

	/**
	 * Activate the edit screen
	 * 
	 * @param selected
	 */
	void onEdit(IModel selected);

	/**
	 * Save object after it has been edited
	 * 
	 * @param selected
	 */
	void onSave(IModel selected);

	/**
	 * Delete object
	 * 
	 * @param selected
	 */
	void onDeleteConfirmed(IModel selected);

	/**
	 * Activate delete-confirmation screen
	 * 
	 * @param selected
	 */
	void onDelete(IModel selected);

	/**
	 * Cancel current screen and go back to previous
	 */
	void onCancel();
}
