package wicket.contrib.yui.examples.pages;

import java.util.ArrayList;

import wicket.contrib.yui.examples.WicketExamplePage;
import wicket.contrib.markup.html.yui.selection.Selection;
import wicket.contrib.markup.html.yui.selection.SelectionSettings;
import wicket.contrib.YuiImage;
import wicket.contrib.YuiProperty;
import wicket.contrib.YuiAttribute;
import wicket.contrib.YuiTextBox;

public class SortPage extends WicketExamplePage{
	private ArrayList imageListA= new ArrayList();
	private ArrayList imageListB= new ArrayList();
	private ArrayList textListA= new ArrayList();
	private ArrayList textListB= new ArrayList();
	
	public SortPage()
	{	
		/**
		 * Define the animation properties
		 */
		
		YuiProperty yuiPropertyBorderTopColor = new YuiProperty("'#FF0000'", "'#FFFFFF'", true);
		YuiProperty yuiPropertyBorderBottomColor = new YuiProperty("'#FF0000'", "'#FFFFFF'", true);
		YuiProperty yuiPropertyBorderLeftColor = new YuiProperty("'#000000'", "'#FFFFFF'", true);
		YuiProperty yuiPropertyBorderRightColor = new YuiProperty("'#000000'", "'#FFFFFF'", true);
		YuiProperty yuiPropertyFontColor = new YuiProperty("'#000000'", "'#FFFFFF'", true);
		YuiProperty yuiPropertyBorderWidth= new YuiProperty("4", "4", true);
		YuiProperty yuiPropertyFontSize= new YuiProperty("18","15", true);
		
		YuiAttribute yuiAttribute= new YuiAttribute();
		
		yuiAttribute.add("borderTopColor", yuiPropertyBorderTopColor);
		yuiAttribute.add("borderBottomColor", yuiPropertyBorderBottomColor);
		yuiAttribute.add("borderLeftColor", yuiPropertyBorderLeftColor);
		yuiAttribute.add("borderRightColor", yuiPropertyBorderRightColor);
		yuiAttribute.add("color", yuiPropertyFontColor);
		yuiAttribute.add("borderWidth", yuiPropertyBorderWidth);
		yuiAttribute.add("fontSize", yuiPropertyFontSize);
		
		imageListA.add(new YuiImage("style/broom.bmp", "This is a Broom"));
		imageListA.add(new YuiImage("style/flower.bmp", "Flower"));
		imageListA.add(new YuiImage("style/window.bmp", "Window"));
		//imageListA.add(new YuiImage("style/fox.gif", "FireFox"));
		add(new Selection("aSelection", SelectionSettings.getDefault(yuiAttribute, "bounceIn", 1, "click", 1, imageListA)));
		
		imageListB.add(new YuiImage("style/broom.bmp", "Broom"));
		imageListB.add(new YuiImage("style/flower.bmp", "Flower"));
		imageListB.add(new YuiImage("style/window.bmp", "Window"));
		add(new Selection("bSelection", SelectionSettings.getDefault(yuiAttribute, "bounceIn", 1, "click", 2, "Only up to 2 selections allowed only!" ,imageListB)));
		
		textListA.add(new YuiTextBox("#8585DB", 80, 80, "Cat"));
		textListA.add(new YuiTextBox("#FFA8A8", 80, 80, "Boy"));
		textListA.add(new YuiTextBox("#B3FFB3", 80, 80, "Apple"));
		add(new Selection("cSelection", SelectionSettings.getDefault(yuiAttribute, "easeOutStrong", 1, "click", 1, textListA)));
		
		textListB.add(new YuiTextBox("#8585DB", 80, 80, "Three"));
		textListB.add(new YuiTextBox("#FFA8A8", 80, 80, "Two"));
		textListB.add(new YuiTextBox("#B3FFB3", 80, 80, "One"));
		add(new Selection("dSelection", SelectionSettings.getDefault(yuiAttribute, "bounceIn", 1, "click", 2, "Up to 2 selections allowed only!" , textListB)));
	}
}
