package wicket.contrib.dojo.widgets;

import java.util.HashMap;

/**
 * Modelise a style Attribute on a tag
 * @author vdemay
 *
 */
public class StyleAttribute extends HashMap<String, String>
{

	public final static String HEIGHT      = "height";
	public final static String WIDTH       = "width";
	public final static String DISPLAY     = "display";
	public final static String MIN_HEIGHT  = "min-height";
	public final static String MIN_WIDTH   = "min-width";
	
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
	
	public void setMinHeight(String minHeight){
		put(MIN_HEIGHT, minHeight);
	}
	
	public void setMinWidth(String minWidth){
		put(MIN_WIDTH, minWidth);
	}
	
	public void setDisplay(String display){
		put(DISPLAY, display);
	}
	
	
	
}
