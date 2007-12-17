package org.wicketstuff.dojo.dojodnd;

import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.wicketstuff.dojo.dojodnd.DojoDragContainer;

/**
 * <p>
 * A container that creates drag sources, but the sources are copied
 * when they are dropped.
 * </p>
 *
 * @author B. Molenkamp
 * @version SVN: $Id$
 */
@SuppressWarnings("serial")
public class DojoDragCopyContainer extends DojoDragContainer {

	private boolean copyOnce;
	
	/**
	 * Constructor of a drag copy container
	 * 
	 * @param id
	 *            widget id
	 */
	public DojoDragCopyContainer(String id) {
		this(id, true);
	}

	/**
	 * Constructor of a drag copy container
	 * 
	 * @param id
	 *            widget id
	 */
	public DojoDragCopyContainer(String id, boolean copyOnce) {
		super(id);
		setCopyOnce(copyOnce);
	}

	/**
	 * Checks if the copied drag source should create copies when it is
	 * dropped. If not, they behave like normal drag sources after they
	 * have been copied.
	 * 
	 * @return the copyOnce
	 */
	public boolean isCopyOnce() {
		return copyOnce;
	}
	
	/**
	 * Enable or disable copy behavior on copied drag sources.
	 * @param copyOnce the copyOnce to set
	 */
	public void setCopyOnce(boolean copyOnce) {
		this.copyOnce = copyOnce;
	}
	
	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.dojodnd.DojoDragContainer#createDragBehavior()
	 */
	@Override
	protected AbstractRequireDojoBehavior createDragBehavior() {
		return new DojoDragCopyContainerHandler();
	}
}
