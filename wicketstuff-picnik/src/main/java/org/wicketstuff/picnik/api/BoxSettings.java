/**
 * 
 */
package org.wicketstuff.picnik.api;

/**
 * 
 * <p>
 * Created 30.03.2008 00:21:59
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class BoxSettings extends AbstractSettings {

	// _close_target: URL for a destination when the user selects "close" (see
	// http://www.picnik.com/info/api/reference/_close_target)
	private String closeTarget;

	// _expand_button: whether to display an expand button for maximizing the Picnik window (see
	// http://www.picnik.com/info/api/reference/_expand_button)
	private boolean expandButton = false;

	@Override
	public BoxSettings fillParameters() {
		addParam("_close_target", getCloseTarget());
		addParam("_expand_button", Boolean.toString(isExpandButton()));
		return this;
	}

	/**
	 * Get the closeTarget.
	 * @return Returns the closeTarget.
	 */
	public String getCloseTarget() {
		return closeTarget;
	}

	/**
	 * Set the closeTarget. _close_target: URL for a destination when the user selects "close"
	 * @see http://www.picnik.com/info/api/reference/_close_target
	 * @param closeTarget The closeTarget to set.
	 */
	public void setCloseTarget(String closeTarget) {
		this.closeTarget = closeTarget;
	}

	/**
	 * Get the expandButton.
	 * @return Returns the expandButton.
	 */
	public boolean isExpandButton() {
		return expandButton;
	}

	/**
	 * Set the expandButton. _expand_button: whether to display an expand button for maximizing the
	 * Picnik window
	 * @see http://www.picnik.com/info/api/reference/_expand_button
	 * @param expandButton The expandButton to set.
	 */
	public void setExpandButton(boolean expandButton) {
		this.expandButton = expandButton;
	}

}
