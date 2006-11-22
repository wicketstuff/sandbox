package wicket.contrib.dojo.dojodnd;

import wicket.markup.html.WebMarkupContainer;

/**
 * Dojo drag container
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoDragContainer extends WebMarkupContainer
{

	private String dragId;
	
	public DojoDragContainer(String id)
	{
		super(id);
		this.setOutputMarkupId(true);
		//all by default
		dragId = "*";
		add(new DojoDragContainerHandler());
	}
	
	public void setDragPattern(String pattern){
		this.dragId = pattern;
	}
	
	public String getDragId(){
		return dragId;
	}

}
