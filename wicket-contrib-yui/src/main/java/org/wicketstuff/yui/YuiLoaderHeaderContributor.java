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
    
    static final String DEFAULT_YUI_BUILD = "2.4.1";
    static final String YUI_BUILD_ROOT = "inc";
    
    
    /**
     * Creates a new instance of YuiLoaderHeaderContributor
     * 
     * is currently in Alpha-State!
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
                            String yahooResource = "http://yui.yahooapis.com/" + DEFAULT_YUI_BUILD + "/build/yuiloader/yuiloader-beta-min.js";
                            
                            ResourceReference yuiRef = new ResourceReference(YuiLoaderHeaderContributor.class, resource);
                            
                            response.renderJavascriptReference(yuiRef);
                            //response.renderJavascriptReference(yahooResource);
                            
//                            response.renderOnLoadJavascript("" +
//                                    " loader = new YAHOO.util.YUILoader(); \n" +
//                                    " loader.require(\"editor\");  \n" +
//                                    " loader.loadOptional = true;  \n" +
//                                    " loader.insert(function() { \n" +
//                                    executeJS + "  \n"+
//                                    " }); \n" +
//                                    " \n"); 
                            
                            // new YUILoader since 2.4
                            response.renderOnLoadJavascript(""+
                                    "var loader = new YAHOO.util.YUILoader({" +
                                    "require: ['editor']," +
                                    "loadOptional: true," +
                                    "onSuccess: function() {"+
                                    ""+ executeJS +
                                    "}"+ 
                                    "}); " +
                                    "" +
                                    "loader.insert();");
                                    
                                    
                            
                            
			}
		});
	} 
    
   
}
