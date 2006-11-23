package wicket.contrib.dojo.widgets;

import java.util.Iterator;
import java.util.Map.Entry;

import wicket.MarkupContainer;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.IModel;

/**
 * {@link WebMarkupContainer} with style attribute
 * @author vdemay
 *
 */
public class StylingWebMarkupContainer extends WebMarkupContainer
{

	private StyleAttribute style;
	
	/**
	 * @param id
	 */
	public StylingWebMarkupContainer(String id)
	{
		super(id);
		style = new StyleAttribute();
	}
	
	/**
	 * @param id
	 * @param model
	 */
	public StylingWebMarkupContainer(String id, IModel model)
	{
		super(id, model);
		style = new StyleAttribute();
	}
	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		onStyleAttribute(style);
		Iterator ite = style.entrySet().iterator();
		String styleTag = "";
		while (ite.hasNext()){
			Entry entry = (Entry)ite.next();
			styleTag += entry.getKey()+":"+entry.getValue()+";";
		}
		tag.put("style", styleTag);	
	}
	
	/**
	 * @param height
	 */
	public final void setHeight(String height){
		style.setHeight(height);
	}
	
	/**
	 * @param width
	 */
	public final void setWidth(String width){
		style.setWidth(width);
	}
	
	/**
	 * @param display
	 */
	public final void setDisplay(String display){
		style.setDisplay(display);
	}
	
	protected void onStyleAttribute(StyleAttribute styleAttribute){
		
	}
	

}
