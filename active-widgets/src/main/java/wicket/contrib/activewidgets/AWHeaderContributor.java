package wicket.contrib.activewidgets;

import java.net.URL;
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
    public static final String DEFAULT_AW_BUILD = "development";
    public static final String AW_BUILD_ROOT = "runtime";
    
	@Override
	public IHeaderContributor[] getHeaderContributors() {
		List<IHeaderContributor> contributors = new ArrayList<IHeaderContributor>();
		IHeaderContributor mainJs = new IHeaderContributor () {

			ResourceReference mainJs = new ResourceReference(AWHeaderContributor.class, AW_BUILD_ROOT + "/" +  DEFAULT_AW_BUILD + "/" + DEFAULT_AW_BUILD + "/lib/aw.js");
			public void renderHead(IHeaderResponse response) {
				response.renderJavascriptReference(mainJs);
			}
			
		};
		
		contributors.add(mainJs);

		IHeaderContributor mainCss = new IHeaderContributor () {
			ResourceReference mainCss = new ResourceReference(AWHeaderContributor.class, AW_BUILD_ROOT + "/" + DEFAULT_AW_BUILD + "/styles/xp/aw.css");
			public void renderHead(IHeaderResponse response) {
				response.renderCSSReference(mainCss);
			}
			
		};
		contributors.add(mainCss);

        return contributors.toArray(new IHeaderContributor[]{});
	}
	
	

/*	
    private String getRealModuleName(String path, String module) {
        final String fullPath = path + "/" + module + "/";
        URL url = getClass().getResource( fullPath + module + ".js");
        if(null != url) {
            return module;
        }
        
        url = getClass().getResource(fullPath + module + "-beta.js");
        
        if(null != url) {
            return module + "-beta";
        }
        
        url = getClass().getResource(fullPath + module + "-experimental.js");
        
        if(null != url) {
            return module + "experimental";
        }
        
        return null;
    }


    public class AWModuleHeaderContributor implements IHeaderContributor {

        private final String name;
        private final String build;
        private final boolean debug;

        public AWModuleHeaderContributor(String name, String build, boolean debug)
        {
            this.name = name;
            this.build = build;
            this.debug = debug;
        }

        public void renderHead(IHeaderResponse response) {
            final String buildPath = AW_BUILD_ROOT + "/" + build;
            
            final String realName = getRealModuleName(buildPath, name);
            
            
            if (null != realName) {
                final String path = buildPath + "/" + name + "/" + realName + ((debug) ? "-debug.js" : ".js");
                final ResourceReference moduleScript;
                response.renderJavascriptReference(moduleScript);
//                response.renderCSSReference(assetRef, "screen");
            } else {
                log.error("Unable to find realName for AW Module " + name);
            }
            
        
			
		}

        
	}
*/	
}
