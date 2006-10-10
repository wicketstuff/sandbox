package wicket.contrib.yui.examples.pages;
import java.util.ArrayList;

import wicket.contrib.YuiEasingConstants;
import wicket.contrib.YuiImage;
import wicket.contrib.markup.html.yui.animselect.AnimSelectOption;
import wicket.contrib.markup.html.yui.animselect.AnimSelectOptionBox;
import wicket.contrib.markup.html.yui.animselect.AnimSelectOptionGroup;
import wicket.contrib.markup.html.yui.animselect.AnimSelectOptionLabel;
import wicket.contrib.markup.html.yui.animselect.AnimSelectSettings;
import wicket.contrib.yui.examples.WicketExamplePage;
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

	/**
	 * Constructor
	 *
	 */
	
	public AnimSelectPage(){

		/*****************************************************************************
		 * Single Selection Example
		 *****************************************************************************/
		
		YuiImage selectedImgSingle = new YuiImage("style/mse_dwn.jpg");
		YuiImage selectedImgOverSingle = new YuiImage("style/mse_dwn.jpg");
		YuiImage defaultImgOverSingle = new YuiImage("style/mse_dwn-1.jpg");
		YuiImage defaultImgSingle = new YuiImage("style/mse_up.jpg");
		
		AnimSelectOption ano1Single = new AnimSelectOption(defaultImgSingle,defaultImgOverSingle, selectedImgSingle, selectedImgOverSingle, "Radio 1");
		AnimSelectOption ano2Single = new AnimSelectOption(defaultImgSingle,defaultImgOverSingle, selectedImgSingle, selectedImgOverSingle, "Radio 2");
		AnimSelectOption ano3Single = new AnimSelectOption(defaultImgSingle,defaultImgOverSingle, selectedImgSingle, selectedImgOverSingle, "Radio 3");

		final ArrayList animSelectOptionListSingle= new ArrayList();
		animSelectOptionListSingle.add(ano1Single);
		animSelectOptionListSingle.add(ano2Single);
		animSelectOptionListSingle.add(ano3Single);
		
		final AnimSelectSettings settingsSingle = AnimSelectSettings.getDefault(YuiEasingConstants.EASE_OUT, 1, 1, animSelectOptionListSingle);
		AnimSelectOptionGroup animSelectOptionGroupSingle= new AnimSelectOptionGroup("animSelectOptionGroupSingle", settingsSingle);
		add(animSelectOptionGroupSingle);
		
		ListView listViewSingle= new ListView("animSelectViewSingle", animSelectOptionListSingle){
			protected void populateItem(ListItem item) {
				AnimSelectOption animSelectOptionSingle= (AnimSelectOption) item.getModelObject();
				item.add(new AnimSelectOptionBox("animSelectOptionBoxSingle", item.getIndex(), animSelectOptionSingle, settingsSingle));
				item.add(new AnimSelectOptionLabel("animSelectOptionLabelSingle", animSelectOptionSingle.getSelectedValue()));
			};
		};
		
		animSelectOptionGroupSingle.add(listViewSingle);
		
		
		/*****************************************************************************
		 * Multiple Selection Example
		 *****************************************************************************/
		
		YuiImage selectedImgMultiple = new YuiImage("style/check.jpg");
		YuiImage selectedImgOverMultiple = new YuiImage("style/check.jpg");
		YuiImage defaultImgOverMultiple = new YuiImage("style/uncheck-1.jpg");
		YuiImage defaultImgMultiple = new YuiImage("style/uncheck.jpg");
		
		AnimSelectOption ano1Multiple = new AnimSelectOption(defaultImgMultiple,defaultImgOverMultiple, selectedImgMultiple, selectedImgOverMultiple, "Check A");
		AnimSelectOption ano2Multiple = new AnimSelectOption(defaultImgMultiple,defaultImgOverMultiple, selectedImgMultiple, selectedImgOverMultiple, "Check B");
		AnimSelectOption ano3Multiple = new AnimSelectOption(defaultImgMultiple,defaultImgOverMultiple, selectedImgMultiple, selectedImgOverMultiple, "Check C");

		final ArrayList animSelectOptionListMultiple= new ArrayList();
		animSelectOptionListMultiple.add(ano1Multiple);
		animSelectOptionListMultiple.add(ano2Multiple);
		animSelectOptionListMultiple.add(ano3Multiple);
		
		
		final AnimSelectSettings settingsMultiple = AnimSelectSettings.getDefault(YuiEasingConstants.EASE_OUT, 1, 2, animSelectOptionListMultiple);
		AnimSelectOptionGroup animSelectOptionGroupMultiple= new AnimSelectOptionGroup("animSelectOptionGroupMultiple", settingsMultiple);
		add(animSelectOptionGroupMultiple);
		
		ListView listViewMultiple= new ListView("animSelectViewMultiple", animSelectOptionListMultiple){
			protected void populateItem(ListItem item) {
				AnimSelectOption animSelectOptionMultiple= (AnimSelectOption) item.getModelObject();
				item.add(new AnimSelectOptionBox("animSelectOptionBoxMultiple", item.getIndex(), animSelectOptionMultiple, settingsMultiple));
				item.add(new AnimSelectOptionLabel("animSelectOptionLabelMultiple", animSelectOptionMultiple.getSelectedValue()));
			};
		};
		
		animSelectOptionGroupMultiple.add(listViewMultiple);
		
	}
}
