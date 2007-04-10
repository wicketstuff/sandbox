package wicket.contrib.dojo.dojodnd;

import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.dojodnd.DojoDragContainer;

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
		super(id);
		setCopyOnce(true);
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
