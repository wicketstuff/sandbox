package wicket.contrib.dojo.markup.html.container;

import wicket.MarkupContainer;
import wicket.contrib.dojo.widgets.StylingWebMarkupContainer;

/**
 * Abstract class to create a DojoContainer
 * @author vincent demay
 *
 */
public abstract class AbstractDojoContainer extends StylingWebMarkupContainer{
	
	private String title;
	
	/**
	 * Construct a dojo container
	 * @param parent parent where this component will be added
	 * @param id component id
	 * @param title component title
	 */
	public AbstractDojoContainer(MarkupContainer parent, String id, String title)
	{
		super(parent, id);
		this.title = title;
	}
	
	/**
	 * Construct a dojo container
	 * @param parent parent where this component will be added
	 * @param id component id
	 */
	public AbstractDojoContainer(MarkupContainer parent, String id)
	{
		super(parent, id);
	}
	
	/**
	 * Return the Container title
	 * @return container title
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Set the container title
	 * @param title container title
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	

}
