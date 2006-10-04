package wicket.contrib.yui.examples.pages;
import wicket.contrib.YuiEasingConstants;
import wicket.contrib.YuiImage;
import wicket.contrib.markup.html.yui.animselect.AnimSelect;
import wicket.contrib.markup.html.yui.animselect.AnimSelectGroupOption;
import wicket.contrib.markup.html.yui.animselect.AnimSelectOption;
import wicket.contrib.markup.html.yui.animselect.AnimSelectSettings;
import wicket.contrib.yui.examples.WicketExamplePage;

/**
 * Examples on how to use the AnimSelect:
 * 		- Animation with mouseover and selection effects
 * 		- Animation with selection effects  
 * 
 * @author cptan
 *
 */

public class AnimSelectPage extends WicketExamplePage {

	/**
	 * Constructor
	 *
	 */
	public AnimSelectPage(){

		YuiImage selectedImg = new YuiImage("style/abcdefgLight_mouseover.bmp");
		YuiImage selectedImgOver = new YuiImage("style/abcdefgLight.bmp");
		YuiImage defaultImgOver = new YuiImage("style/abcdefgDark_mouseover.bmp");
		YuiImage defaultImg = new YuiImage("style/abcdefgDark.bmp");
		
		AnimSelectOption ano1 = new AnimSelectOption(defaultImg,defaultImgOver, selectedImg, selectedImgOver, "Option 1");
		AnimSelectOption ano2 = new AnimSelectOption(defaultImg,defaultImgOver, selectedImg, selectedImgOver, "Option 2");
		AnimSelectOption ano3 = new AnimSelectOption(defaultImg,defaultImgOver, selectedImg, selectedImgOver, "Option 3");
		
		//need to allow the user to specify the positioning of the label and the option.		
		//this is a problem as user need to specify the positioning of the image correctly
		AnimSelectGroupOption yago = new AnimSelectGroupOption();
		yago.setHideLabel(true);
		yago.add(ano1);
		yago.add(ano2);
		yago.add(ano3);

		add(new AnimSelect("aSelection", AnimSelectSettings.getDefault(YuiEasingConstants.EASE_OUT, 1, 1, yago)));
		
		
//		add(new AnimSelect("aSelection") {
//			public void populateItem (Object item)
//			{
//				AnimSelectOption option = item.getModelObject();
//				item.add(option);
//				item.add("label" option.getSelectio)
//			}
//			
//		});
		
		
		
		
		
		add(new AnimSelect("bSelection", AnimSelectSettings.getDefault(YuiEasingConstants.EASE_OUT, 1, 2, "up to 2 selections", yago)));

		YuiImage homeSelectedImg = new YuiImage("style/abcdefgLight.bmp");
		YuiImage homeSelectedImgOver = new YuiImage("style/abcdefgLight.bmp");
		YuiImage homeDefaultImgOver = new YuiImage("style/abcdefgDark.bmp");
		YuiImage homeDefaultImg = new YuiImage("style/abcdefgDark.bmp");
		
		AnimSelectOption ano01 = new AnimSelectOption(homeDefaultImg,homeDefaultImgOver, homeSelectedImg, homeSelectedImgOver, "Option 1");
		AnimSelectOption ano02 = new AnimSelectOption(homeDefaultImg,homeDefaultImgOver, homeSelectedImg, homeSelectedImgOver, "Option 2");
		AnimSelectOption ano03 = new AnimSelectOption(homeDefaultImg,homeDefaultImgOver, homeSelectedImg, homeSelectedImgOver, "Option 3");
		AnimSelectOption ano04 = new AnimSelectOption(homeDefaultImg,homeDefaultImgOver, homeSelectedImg, homeSelectedImgOver, "Option 4");
		AnimSelectOption ano05 = new AnimSelectOption(homeDefaultImg,homeDefaultImgOver, homeSelectedImg, homeSelectedImgOver, "Option 5");

		AnimSelectGroupOption yag = new AnimSelectGroupOption();
		yag.add(ano01);
		yag.add(ano02);
		yag.add(ano03);
		yag.add(ano04);
		yag.add(ano05);

		add(new AnimSelect("cSelection", AnimSelectSettings.getDefault(YuiEasingConstants.EASE_OUT, 1, 1, yag)));
		add(new AnimSelect("dSelection", AnimSelectSettings.getDefault(YuiEasingConstants.EASE_OUT, 1, 3, yag)));
	}
}
