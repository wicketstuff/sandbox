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
	private static final CompressedResourceReference MOOTOOLS = 
		new CompressedResourceReference(AbstractRequireMooBehavior.class,"mootools.v1.1.js");
	
	/**
	 * Uncompressed Mootools Javascript reference
	 */
	private static final CompressedResourceReference MOOTOOLS_UNCOMPRESSED = 
		new CompressedResourceReference(AbstractRequireMooBehavior.class,"mootools-1.v1-uncompressed.js");
	
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
	 * @return
	 */
	public static MooBase getInstance() {
		if(mooBase == null)
			mooBase = new MooBase();
		
		return mooBase;
	}
	
	/**
	 * Check to see if component is mooBindable
	 * @param c
	 * @return
	 */
	public Boolean isMooBindable(Component c) {
		Method[] m = c.getClass().getDeclaredMethods();
		for(int i = 0 ; i < m.length; i ++)
		{
			System.out.println(m[i].getName());
			if(m[i].getName().equals("mooFunction"))
				return true;
		}
		return false;
	}
	
	/**
	 * Grab a MooTools file reference
	 * @return
	 */
	public ResourceReference getMootoolsReference() {
		if(Application.get().getMetaData(USE_CUSTOM_MOOTOOLS)==null || !(Application.get().getMetaData(USE_CUSTOM_MOOTOOLS) instanceof CompressedResourceReference)) {
			if(USE_MOOTOLS_UNCOMPRESSED)
				return MOOTOOLS_UNCOMPRESSED;
			else
				return MOOTOOLS;
		} else {
			return (CompressedResourceReference)Application.get().getMetaData(USE_CUSTOM_MOOTOOLS);
		}
	}
}
