package wicket.contrib.activewidgets.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveWidgetsConfiguration {
	
	public static final Logger log = LoggerFactory.getLogger(ActiveWidgetsConfiguration.class);
    public static final String AW_VERSION;
    public static final String LATEST_AW_VERSION = "2.5";
	public static final String KEY_AW_VERSION = "active.widgets.version";
	public static final String KEY_AW_LICENSE = "active.widgets.license";
    public static final String AW_BUILD_ROOT = "runtime";
    public static final String AW_DEVELOPER_LICENSE = "developer";
    public static final String AW_TRIAL_LICENSE = "trial";
	private static final boolean DEFAULT_STRICT_XHTML = false;
    public static       String AW_LICENSE_TYPE;
	public static       String AW_LIB_HOME_PATH;
	private static boolean strictXtml = DEFAULT_STRICT_XHTML;
    

	
	/**
	 * Active Widgets version and license type could be override by server startup.
	 * Use -Dactive.widgets.version and -Dactive.widgets.license JVM's arguments. 
	 * Be sure, runtime of the selected version and license exists in the deploy folders.
	 */
    static { 
    	
		// look for the used version
    	String awVersion = System.getProperty(KEY_AW_VERSION);
    	if (awVersion != null && !"".equals(awVersion)) {
    		AW_VERSION = awVersion;
    	} else {
    		AW_VERSION = LATEST_AW_VERSION;
    	}
    	
    	// look for the license key
   		setLicenseType(AW_LICENSE_TYPE = System.getProperty(KEY_AW_LICENSE));
    	
    }
    
	
	public enum CreateMode {
		DOCUMENT_WRITE,
		MARKUP
	}


	public static void setLicenseType(String licenseType) {
		if (licenseType == null) {
			setLicenseType(AW_TRIAL_LICENSE);
			return;
		}
		AW_LICENSE_TYPE = licenseType;
		AW_LIB_HOME_PATH = AW_BUILD_ROOT + "/" +  AW_VERSION + "/" +  AW_LICENSE_TYPE;
		
		/*
		 * Comment out the auto switching to trial, while in does not works correctly. 
		 * Why? not yet clear. Eclipse or may be Jetty.
		 * Possible need to explore ((WebApplication) Application.get()).getServletContext().getResource()
		 * 
    	File path = new File (((WebApplication) Application.get()).getServletContext().getRealPath(AW_LIB_HOME_PATH));
    	if (!path.exists()) {
    		if (licenseType.equals(AW_TRIAL_LICENSE)) {
    			log.error("Active Widgets is not accessible. Can not find runtime library.");
    		} else {
    			log.warn("Can not find " + licenseType + " runtime Active Widgets library. Instead of trial version try to be used. Check settings or/and code.");
    			// try trial version
    			setLicenseType(AW_TRIAL_LICENSE);
    		}
    		return;
		}
		 */
    	log.info("Used Active Widget library: \"" + getLicenseMsg()  + "\" version");
    	log.debug("Path to Active Widget library: " + AW_LIB_HOME_PATH);
	}
	
	private static String getLicenseMsg () {
		
		final StringBuffer awLicenseMsg = new StringBuffer();

		awLicenseMsg.append(AW_VERSION).append(" ");
		if ( AW_LICENSE_TYPE.equals (AW_DEVELOPER_LICENSE)) {
			awLicenseMsg.append("Developer license");
		} else 
		if ( AW_LICENSE_TYPE.equals (AW_TRIAL_LICENSE)) {
			awLicenseMsg.append("Free Trial");
		} else {
			awLicenseMsg.append(AW_LICENSE_TYPE);
		}
		return awLicenseMsg.toString();
		
	}

	public static class Grid {
		public static boolean setConfiguration(String configKey,
				String configValue) {
			return false;
		}
		
	}

	public static CreateMode getDefaultCreateMode() {
		return CreateMode.MARKUP;
	}

	public static boolean isStrictXtml() {
		return strictXtml;
	}

	public static void setStrictXtml(boolean strictXtml) {
		ActiveWidgetsConfiguration.strictXtml = strictXtml;
	}

	public static boolean isTrial() {
		return AW_LICENSE_TYPE.equalsIgnoreCase(AW_TRIAL_LICENSE);
	}

}
