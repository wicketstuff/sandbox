/*
 * YuiLoaderHeaderContributor.java
 *
 * Created on 9. September 2007, 20:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wicketstuff.yui;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 *
 * @author korbinianbachl
 */
public class YuiLoaderHeaderContributor {
    
    static final String DEFAULT_YUI_BUILD = "2.3.0";
    static final String YUI_BUILD_ROOT = "inc";
    
    
    /**
     * Creates a new instance of YuiLoaderHeaderContributor
     */
    public YuiLoaderHeaderContributor() {
    
    }
    
    
    public static final HeaderContributor forModule(final String module, final String executeJS)
	{
		return new HeaderContributor(new IHeaderContributor()
		{
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response)
			{
                            String resource = YUI_BUILD_ROOT + "/" + DEFAULT_YUI_BUILD + "/yuiloader/yuiloader-beta-min.js";
                            String yahooResource = "http://yui.yahooapis.com/2.3.0/build/yuiloader/yuiloader-beta-min.js";
                            
                            ResourceReference yuiRef = new ResourceReference(YuiLoaderHeaderContributor.class, resource);
                            
                            response.renderJavascriptReference(yuiRef);
                            //response.renderJavascriptReference(yahooResource);
                            
                            response.renderOnLoadJavascript("" +
                                    " loader = new YAHOO.util.YUILoader(); \n" +
                                    " loader.require(\"editor\");  \n" +
                                    " loader.loadOptional = true;  \n" +
                                    " loader.insert(function() { \n" +
                                    executeJS + "  \n"+
                                    " }); \n" +
                                    " \n");
			}
		});
	} 
    
   
}
