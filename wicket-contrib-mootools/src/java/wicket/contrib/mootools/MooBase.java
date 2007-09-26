package wicket.contrib.mootools;

import java.lang.reflect.Method;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;

public class MooBase {
	/**
	 * Singleton instance for this class
	 */
	private static MooBase mooBase;

	/**
	 * Compressed MooTools Javascript reference
	 */
	private static final CompressedResourceReference MOOTOOLS = new CompressedResourceReference(
			AbstractRequireMooBehavior.class, "mootools-release-1.11.js");

	/**
	 * Uncompressed Mootools Javascript reference
	 */
	private static final CompressedResourceReference MOOTOOLS_UNCOMPRESSED = new CompressedResourceReference(
			AbstractRequireMooBehavior.class, "mootools-release-1.11-uncompressed.js");

	/**
	 * Boolean to determine if to use compressed or uncompressed mootools source
	 */
	public static boolean USE_MOOTOLS_UNCOMPRESSED = false;

	/**
	 * a Unique key to know if a CompressedResourceReference is set by the user
	 * if to use a unique MOOTOOLS script
	 */
	private static final MetaDataKey USE_CUSTOM_MOOTOOLS = new MetaDataKey(CompressedResourceReference.class) {
		private static final long serialVersionUID = 1L;
	};

	private MooBase() {
	}

	/**
	 * Get MooBase singleton
	 * 
	 * @return
	 */
	public static MooBase getInstance() {
		if (mooBase == null) {
			mooBase = new MooBase();
		}

		return mooBase;
	}

	/**
	 * Check to see if component is mooBindable
	 * 
	 * @param c
	 * @return
	 */
	public Boolean isMooBindable(final Component c) {
		Method[] m = c.getClass().getDeclaredMethods();
		for (Method element : m) {
			System.out.println(element.getName());
			if (element.getName().equals("mooFunction")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Grab a MooTools file reference
	 * 
	 * @return
	 */
	public ResourceReference getMootoolsReference() {
		if (Application.get().getMetaData(USE_CUSTOM_MOOTOOLS) == null
				|| !(Application.get().getMetaData(USE_CUSTOM_MOOTOOLS) instanceof CompressedResourceReference)) {
			if (USE_MOOTOLS_UNCOMPRESSED) {
				return MOOTOOLS_UNCOMPRESSED;
			} else {
				return MOOTOOLS;
			}
		} else {
			return (CompressedResourceReference) Application.get().getMetaData(USE_CUSTOM_MOOTOOLS);
		}
	}
}
