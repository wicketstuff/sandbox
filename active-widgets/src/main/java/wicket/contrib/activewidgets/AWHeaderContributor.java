package wicket.contrib.activewidgets;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractHeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AWHeaderContributor extends AbstractHeaderContributor {


	private static final Logger log = LoggerFactory.getLogger(AWHeaderContributor.class);
    public static final String AW_LICENSE_TYPE;
    public static final String AW_VERSION;
    public static final String LATEST_AW_VERSION = "2.5";
	private static final String KEY_AW_VERSION = "active.widgets.version";
	private static final String KEY_AW_LICENSE = "active.widgets.license";
    public static final String AW_BUILD_ROOT = "runtime";
    public static final String AW_DEVELOPER_LICENSE = "developer";
	private static final String AW_LIB_HOME_PATH;
    
	/**
	 * Active Widgets version and license type could be override by server startup.
	 * Use -Dactive.widgets.version and -Dactive.widgets.license JVM's arguments. 
	 * Be sure, runtime of the selected version and license exists in the deploy folders.
	 */
    static { 
    	StringBuffer awLicenseMsg = new StringBuffer();
    	
		// look for the used version
    	String awVersion = System.getProperty(KEY_AW_VERSION);
    	if (awVersion != null && !"".equals(awVersion)) {
    		AW_VERSION = awVersion;
    	} else {
    		AW_VERSION = LATEST_AW_VERSION;
    	}
		awLicenseMsg.append(AW_VERSION).append(" ");
    	
    	// look for the license key
		String awLicense = System.getProperty(KEY_AW_LICENSE);
		if (awLicense != null && AW_DEVELOPER_LICENSE.equals(awLicense)) {
    		awLicenseMsg.append("Developer License");
    		AW_LICENSE_TYPE = awLicense;
    	} else {
    		AW_LICENSE_TYPE = "trial";
    		awLicenseMsg.append("Free Trial");
		}
		
    	log.info("Used Active Widget library: \"" + awLicenseMsg.toString() + "\" version");
    	AW_LIB_HOME_PATH = AW_BUILD_ROOT + "/" +  AW_VERSION + "/" +  AW_LICENSE_TYPE;
    	log.debug("Path to Active Widget library: " + AW_LIB_HOME_PATH);
    }
    
    
	@Override
	public IHeaderContributor[] getHeaderContributors() {
		List<IHeaderContributor> contributors = new ArrayList<IHeaderContributor>();
		IHeaderContributor mainJs = new IHeaderContributor () {

			ResourceReference mainJs = new ResourceReference(AWHeaderContributor.class, AW_LIB_HOME_PATH + "/lib/aw.js");
			public void renderHead(IHeaderResponse response) {
				response.renderJavascriptReference(mainJs);
			}
			
		};
		
		contributors.add(mainJs);

		IHeaderContributor mainCss = new IHeaderContributor () {
			ResourceReference mainCss = new ResourceReference(AWHeaderContributor.class, AW_LIB_HOME_PATH + "/styles/xp/aw.css");
			public void renderHead(IHeaderResponse response) {
				response.renderCSSReference(mainCss);
			}
			
		};
		contributors.add(mainCss);

        return contributors.toArray(new IHeaderContributor[]{});
	}
	
}
