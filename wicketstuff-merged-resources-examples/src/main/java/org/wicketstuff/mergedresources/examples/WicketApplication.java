package org.wicketstuff.mergedresources.examples;

import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.mergedresources.ResourceMount;
import org.wicketstuff.mergedresources.examples.components.ComponentB;
import org.wicketstuff.mergedresources.examples.components.MyForm;
import org.wicketstuff.mergedresources.examples.components.PanelOne;
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
		//getResourceSettings().setAddLastModifiedTimeToResourceReferenceUrl(true);
		
		if (merge()) {		
			ResourceMount.mountWicketResources("script", this);
			
			ResourceMount mount = new ResourceMount()
				.setResourceVersionProvider(new RevisionVersionProvider());
			
			mount.clone()
				.setPath("/style/all.css")
				.addResourceSpecsMatchingSuffix(PanelOne.class, ComponentB.class, MyForm.class)
				.mount(this);
			
			mount.clone()
				.setPath("/style/all.js")
				.addResourceSpecsMatchingSuffix(PanelOne.class, ComponentB.class, MyForm.class)
				.mount(this);
		}
	}

	protected boolean strip() {
		return true;
	}

	protected boolean merge() {
		return true;
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	public Class<?> getHomePage()
	{
		return HomePage.class;
	}

}
