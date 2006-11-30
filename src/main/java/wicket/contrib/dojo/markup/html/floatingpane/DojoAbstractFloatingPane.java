package wicket.contrib.dojo.markup.html.floatingpane;

import wicket.contrib.dojo.widgets.StylingWebMarkupContainer;
import wicket.markup.ComponentTag;

public abstract class DojoAbstractFloatingPane extends StylingWebMarkupContainer
{
	
	private String title;
	private boolean rezisable;
	private boolean displayMinimizeAction;
	private boolean displayMaximizeAction;
	private boolean displayCloseAction;
	private boolean hasShadow;

	public DojoAbstractFloatingPane( String id)
	{
		super(id);
		title = "";
		rezisable = true;
		displayCloseAction = true;
		displayMaximizeAction = true;
		displayCloseAction = true;
		hasShadow = false;
	}
	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("title", title);
		
		if (rezisable){
			tag.put("rezisable", "true");
		}
		else{
			tag.put("rezisable", "false");
		}
		
		if (displayMinimizeAction){
			tag.put("displayMinimizeAction", "true");
		}
		else{
			tag.put("displayMinimizeAction", "false");
		}
		
		if (displayMaximizeAction){
			tag.put("displayMaximizeAction", "true");
		}
		else{
			tag.put("displayMaximizeAction", "false");
		}
		
		if (displayCloseAction){
			tag.put("displayCloseAction", "true");
		}
		else{
			tag.put("displayCloseAction", "false");
		}
		
		if (hasShadow){
			tag.put("hasShadow", "true");
		}
		else{
			tag.put("hasShadow", "false");
		}
	}


	public boolean isDisplayCloseAction()
	{
		return displayCloseAction;
	}


	public void setDisplayCloseAction(boolean displayCloseAction)
	{
		this.displayCloseAction = displayCloseAction;
	}


	public boolean isDisplayMaximizeAction()
	{
		return displayMaximizeAction;
	}


	public void setDisplayMaximizeAction(boolean displayMaximizeAction)
	{
		this.displayMaximizeAction = displayMaximizeAction;
	}


	public boolean isDisplayMinimizeAction()
	{
		return displayMinimizeAction;
	}


	public void setDisplayMinimizeAction(boolean displayMinimizeAction)
	{
		this.displayMinimizeAction = displayMinimizeAction;
	}


	public boolean isHasShadow()
	{
		return hasShadow;
	}


	public void setHasShadow(boolean hasShadow)
	{
		this.hasShadow = hasShadow;
	}


	public boolean isRezisable()
	{
		return rezisable;
	}


	public void setRezisable(boolean rezisable)
	{
		this.rezisable = rezisable;
	}


	public String getTitle()
	{
		return title;
	}


	public void setTitle(String title)
	{
		this.title = title;
	}

}
