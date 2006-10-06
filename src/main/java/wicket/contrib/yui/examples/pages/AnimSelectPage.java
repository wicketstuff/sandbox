package wicket.contrib.yui.examples.pages;
import java.util.ArrayList;

import wicket.contrib.YuiEasingConstants;
import wicket.contrib.YuiImage;
import wicket.contrib.markup.html.yui.animselect.AnimSelect;
import wicket.contrib.markup.html.yui.animselect.AnimSelectGroupOption;
import wicket.contrib.markup.html.yui.animselect.AnimSelectOption;
import wicket.contrib.markup.html.yui.animselect.AnimSelectSettings;
import wicket.contrib.yui.examples.WicketExamplePage;
import wicket.markup.MarkupStream;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

/**
 * Examples on how to use the AnimSelect:
 * 		- Animation with mouseover and selection effects
 * 		- Animation with selection effects  
 * 
 * @author cptan
 *
 */

public class AnimSelectPage extends WicketExamplePage {
	private ListView animSelectListView;
	private AnimSelectGroupOption ansGroup;
	
	public AnimSelectPage(){

		YuiImage selectedImg = new YuiImage("style/abcdefgLight_mouseover.bmp");
		YuiImage selectedImgOver = new YuiImage("style/abcdefgLight.bmp");
		YuiImage defaultImgOver = new YuiImage("style/abcdefgDark_mouseover.bmp");
		YuiImage defaultImg = new YuiImage("style/abcdefgDark.bmp");
		
		AnimSelectOption ano1 = new AnimSelectOption(defaultImg,defaultImgOver, selectedImg, selectedImgOver, "Option 1");
		AnimSelectOption ano2 = new AnimSelectOption(defaultImg,defaultImgOver, selectedImg, selectedImgOver, "Option 2");
		AnimSelectOption ano3 = new AnimSelectOption(defaultImg,defaultImgOver, selectedImg, selectedImgOver, "Option 3");
		
		final ArrayList list= new ArrayList();
		list.add(ano1);
		list.add(ano2);
		list.add(ano3);
		 
		add(ansGroup = new AnimSelectGroupOption("aSelection", list) {
			public void populateItem(ListItem item) {
				AnimSelectOption option= (AnimSelectOption)item.getModelObject();
				item.add(new AnimSelect("box", option, AnimSelectSettings.getDefault(YuiEasingConstants.EASE_OUT, 1, 1, list)));
				item.add(new Label("label", option.getSelectedValue()));
			}
			protected void onRender(MarkupStream markupStream) {
				System.out.println(markupStream.toString());
				markupStream.skipComponent();
			}
		});

//		add(animSelectListView = new ListView("aSelection", list){
//			public void populateItem(ListItem item) {
//				AnimSelectOption option= (AnimSelectOption)item.getModelObject();
//				item.add(new AnimSelect("box", option, AnimSelectSettings.getDefault(YuiEasingConstants.EASE_OUT, 1, 1, list)));
//				item.add(new Label("label", option.getSelectedValue()));
//			}
//		});
//	
//		add(animSelectListView = new ListView("bSelection", list){
//			public void populateItem(ListItem item) {
//				AnimSelectOption option= (AnimSelectOption)item.getModelObject();
//				item.add(new AnimSelect("box", option, AnimSelectSettings.getDefault(YuiEasingConstants.EASE_OUT, 1, 2, list)));
//				item.add(new Label("label", option.getSelectedValue()));
//			}
//		});
		
	}
}
