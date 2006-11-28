package wicket.contrib.dojo.markup.html.container.page;

import wicket.MarkupContainer;
import wicket.Page;
import wicket.PageParameters;
import wicket.contrib.dojo.markup.html.container.DojoSimpleContainer;
import wicket.markup.ComponentTag;

/**
 * Container able to render a page in it
 * @author Vincent Demay
 *
 */
public class DojoPageContainer extends DojoSimpleContainer
{
	
	private Class pageClass;
	private String refresh = "false"; 
	
	/**
	 * Construct a DojoPageContainer
	 * @param id container id
	 * @param title container title
	 */
	public DojoPageContainer(final String id, final String title)
	{
		super(id, title);
	}

	/**
	 * Construct a DojoPageContainer
	 * @param id container id
	 */
	public DojoPageContainer(String id)
	{
		super(id);
	}
	
	/**
	 * Construct a DojoPageContainer
	 * @param id container id
	 * @param pageClass class representing the page class to render in the component
	 */
	public DojoPageContainer(String id, Class pageClass)
	{
		super(id);
		this.pageClass = pageClass;
	}
	
	/**
	 * 
	 * @param parent parent where this widget is added
	 * @param id container id
	 * @param title container title
	 * @param pageClass class representing the page class to render in the component
	 */
	public DojoPageContainer(String id, String title, Class  pageClass)
	{
		super(id, title);
		this.pageClass = pageClass;
	}
	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("href", urlFor(pageClass, new PageParameters()));
		tag.put("refreshOnShow", getRefresh());
	}

	/**
	 * return the page class render in the container
	 * @return the page class render in the container
	 */
	public Class getPageClass()
	{
		return pageClass;
	}

	/**
	 * set the page class to render in the container
	 * @param pageClass page class to render in the container
	 */
	public void setPageClass(Class pageClass)
	{
		this.pageClass = pageClass;
	}
	
	/**
	 * Set true to autorefresh content each time the container is shown
	 * @param refresh true to autorefresh content each time the container is shown false otherwise
	 */
	public void setRefresh(boolean refresh){
		if (refresh){
			this.refresh = "true";
		}else{
			this.refresh = "false";
		}
	}
	
	/**
	 * return true to autorefresh content each time the container is shown false otherwise
	 * @return true to autorefresh content each time the container is shown false otherwise
	 */
	public boolean getRefresh(){
		if ("true".equals(this.refresh)){
			return true;
		}else{
			return false;
		}
	}

}
