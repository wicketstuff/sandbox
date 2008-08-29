package org.wicketstuff.mergedresources.examples;

import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.mergedresources.ResourceMountHelper;
import org.wicketstuff.mergedresources.examples.components.ComponentB;
import org.wicketstuff.mergedresources.examples.components.MyForm;
import org.wicketstuff.mergedresources.examples.components.PanelOne;
import org.wicketstuff.mergedresources.versioning.IResourceVersionProvider;
import org.wicketstuff.mergedresources.versioning.RevisionVersionProvider;


/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    
    /**
     * Constructor
     */
	public WicketApplication()
	{
	}
	
	
	
	@Override
	protected void init() {
		getResourceSettings().setStripJavascriptCommentsAndWhitespace(strip());
		getResourceSettings().setAddLastModifiedTimeToResourceReferenceUrl(true);
		
		if (merge()) {
			IResourceVersionProvider p = new RevisionVersionProvider();
			ResourceMountHelper h = new ResourceMountHelper(this, p);
			
			h.mountMergedSharedResource("style", "all.css", true,
				    new Class<?>[] {PanelOne.class, ComponentB.class, MyForm.class},
				    new String[] {"PanelOne.css", "ComponentB.css", "MyForm.css" });
			
			h.mountMergedSharedResource("script", "all.js", true,
				    new Class<?>[] {PanelOne.class, ComponentB.class, MyForm.class},
				    new String[] {"PanelOne.js", "ComponentB.js", "MyForm.js" });
		}
	}

	protected boolean strip() {
		return true;
	}

	protected boolean merge() {
		return false;
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	public Class<?> getHomePage()
	{
		return HomePage.class;
	}

}
