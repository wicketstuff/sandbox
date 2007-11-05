
package org.wicketstuff.examples.rome.feeds;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Resource;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.SharedResources;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.protocol.http.*;
import org.wicketstuff.rome.FeedResource;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * 
 * 
 * @author Sean C. Sullivan
 *
 */
public class FeedManager
{
	static String getDefaultFeedType()
	{
		return "rss_2.0";
	}
	
	/* 
	  
	 todo (?)
	  
	static public String getFeedUrl(Class feedClass)
	{
		WebApplication webApp = (WebApplication) WebApplication.get();
		
		WebRequestCycle cycle = (WebRequestCycle) WebRequestCycle.get();
		
		WebRequest request = cycle.getWebRequest();
		
		HttpServletRequest httpRequest = request.getHttpServletRequest();
		
		ServletContext context = webApp.getServletContext();
		
		StringBuffer url = new StringBuffer();
		url.append( httpRequest.isSecure() ? "https" : "http");
		url.append("://");
		url.append(httpRequest.getServerName());
		url.append(":");
		url.append(httpRequest.getServerPort());
		url.append("/");
		url.append(httpRequest.getContextPath());
		url.append("/");
		url.append(request.getPath());
		url.append("/");
		
		String feedPath = ???; 
		
		return "todo";
	}
	
	*/
	
	static public ResourceReference createResourceReference(final Class feedClass)
	{
		return new ResourceReference(getResourceName(feedClass))
		{
			protected Resource newResource()
			{
				return createFeedResource(feedClass);
			}
			
		};
	}
	
	static public HeaderContributor createAutodiscoveryLink(Class feedClass)
	{
		HeaderContributor contributor = FeedResource.autodiscoveryLink(createResourceReference(feedClass));
		return contributor;
	}
	
	static public FeedResource createFeedResource(final Class feedClass)
	{
		return new FeedResource()
		{

			protected SyndFeed getFeed()
			{
				return createFeed(feedClass);
			}
			
		};
	}

	static public String getResourceName(Class feedClass)
	{
		return feedClass.getName();
	}

	
	private static SyndFeed createFeed(Class feedClass)
	{
		try
		{
			Object obj = feedClass.newInstance();
			return (SyndFeed) obj;
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
		
	}
	
	static public void mountFeed(WebApplication app, Class feedClass, String feedPath)
	{
		SharedResources resources = app.getSharedResources();
		
		String resourceName = getResourceName(feedClass);
		FeedResource resource = createFeedResource(feedClass);
		
		resources.add(resourceName, resource);
		
		ResourceReference ref = createResourceReference(feedClass);
		
		app.mountSharedResource(feedPath, ref.getSharedResourceKey());
		
	}
	
}
