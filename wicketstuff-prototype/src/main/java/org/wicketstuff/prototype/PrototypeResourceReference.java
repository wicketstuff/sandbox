package org.wicketstuff.prototype;

import org.apache.wicket.Application;
import org.apache.wicket.Resource;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

/**
 * A reference to the "prototype.js" script.
 */
public final class PrototypeResourceReference extends
		JavascriptResourceReference {

	/**
	 * Singleton instance of this reference
	 */
	public static final ResourceReference INSTANCE = new PrototypeResourceReference();

	public static final String NAME = "prototype.js";

	private PrototypeResourceReference() {
		super(PrototypeResourceReference.class, NAME);
	}

	/**
	 * Use a {@link DefaultPrototypeResource} if no resource was installed on
	 * the {@link Application}.
	 * 
	 * @see #install(Application, Resource)
	 */
	@Override
	protected Resource newResource() {
		return new DefaultPrototypeResource();
	}

	/**
	 * Install the given resource to be used for the prototypes script.
	 * 
	 * @param application
	 *            the application to install the resource on
	 * @param resource
	 *            the prototypes script resource
	 */
	public static void install(final Application application,
			final Resource resource) {
		application.getSharedResources().add(PrototypeResourceReference.class,
				NAME, null, null, resource);
	}
}
