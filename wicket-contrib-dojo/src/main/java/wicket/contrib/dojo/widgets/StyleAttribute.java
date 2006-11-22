package wicket.contrib.dojo.widgets;

import java.util.HashMap;

/**
 * Modelise a style Attribute on a tag
 * @author vdemay
 *
 */
public class StyleAttribute extends HashMap
{

	public final static String HEIGHT      = "height";
	public final static String WIDTH       = "width";
	public final static String DISPLAY     = "display";
	
	/**
	 * Constructor
	 */
	public StyleAttribute()
	{
		super();
	}
	
	public void setHeight(String height){
		put(HEIGHT, height);
	}
	
	public void setWidth(String width){
		put(WIDTH, width);
	}
	
	public void setDisplay(String display){
		put(DISPLAY, display);
	}
	
	
	
}
