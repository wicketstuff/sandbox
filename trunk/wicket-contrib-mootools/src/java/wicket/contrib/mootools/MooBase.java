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
	 * Mootool's trunk for bleeding edge
	 */
	private static final CompressedResourceReference MOOTOOLS_TRUNK = new CompressedResourceReference(
			AbstractRequireMooBehavior.class, "mootools-trunk.js");

	/**
	 * Boolean to determine if to use compressed or uncompressed mootools source
	 */
	public static boolean USE_MOOTOOLS_UNCOMPRESSED = false;

	/**
	 * Use the latest and greatest mootools
	 */
	public static boolean USE_MOOTOOLS_TRUNK = true;

	/**
	 * a Unique key to know if a CompressedResourceReference is set by the user
	 * if to use a unique MOOTOOLS script
	 */
	public static final MetaDataKey USE_CUSTOM_MOOTOOLS = new MetaDataKey(CompressedResourceReference.class) {
		private static final long serialVersionUID = 1L;
	};

	public static final MetaDataKey USE_TRUNK_MOOTOOLS = new MetaDataKey(Boolean.class) {
		private static final long serialVersionUID = 1L;
	};

	public static final MetaDataKey USE_UNCOMPRESSED_MOOTOOLS = new MetaDataKey(Boolean.class) {
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

			if (Application.get().getMetaData(USE_UNCOMPRESSED_MOOTOOLS) != null) {
				if ((Boolean) Application.get().getMetaData(USE_UNCOMPRESSED_MOOTOOLS)) {
					return MOOTOOLS_UNCOMPRESSED;
				}
			}
			if (Application.get().getMetaData(USE_TRUNK_MOOTOOLS) != null) {
				if ((Boolean) Application.get().getMetaData(USE_TRUNK_MOOTOOLS)) {
					return MOOTOOLS_TRUNK;
				}
			}
			return MOOTOOLS;
		} else {
			return (CompressedResourceReference) Application.get().getMetaData(USE_CUSTOM_MOOTOOLS);
		}
	}
}
