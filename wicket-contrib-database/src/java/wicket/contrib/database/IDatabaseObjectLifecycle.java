/**
 * Copyright (C) 2006, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

/**
 * Interface for receiving notifications of significant events.
 *
 * @author Jonathan Locke
 */
public interface IDatabaseObjectLifecycle
{
	/**
	 * The object is about to be saved
	 */
	void onSave();
	
	/**
	 * The object is about to be updated
	 */
	void onUpdate();
}
