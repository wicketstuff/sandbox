package wicket.contrib.yui.examples.pages;
import java.util.ArrayList;

import wicket.contrib.YuiEasingConstants;
import wicket.contrib.YuiImage;
import wicket.contrib.markup.html.yui.anim.AnimBox;
import wicket.contrib.markup.html.yui.anim.AnimGroup;
import wicket.contrib.markup.html.yui.anim.AnimLabel;
import wicket.contrib.markup.html.yui.anim.AnimOption;
import wicket.contrib.markup.html.yui.anim.AnimSettings;
import wicket.contrib.yui.examples.WicketExamplePage;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

/**
 * This class demostrates how you can use the wicket.contrib.markup.html.yui.anim package
 * to create animated inputs like radio buttons and check boxes. 
 * <p>
 * 
 * @author cptan
 *
 */
public class AnimPage extends WicketExamplePage {

	/**
	 * Defines an AnimPage object
	 *
	 */
	public AnimPage(){
		
		/*
		 * Example 1: Single option only
		 */
		
		//Step1: Define the images for the selected, selected mouseover, unselected and unselected mouseover images
		YuiImage selectedImgSingle = new YuiImage("style/mse_dwn-1.jpg");
		YuiImage selectedImgOverSingle = new YuiImage("style/mse_dwn-1.jpg");
		YuiImage defaultImgOverSingle = new YuiImage("style/mse_dwn.jpg");
		YuiImage defaultImgSingle = new YuiImage("style/mse_up.jpg");
		
		//Step 2: Define the options for the options, user may specify infinite number of options
		AnimOption ao1Single = new AnimOption(defaultImgSingle,defaultImgOverSingle, selectedImgSingle, selectedImgOverSingle, "Radio 1");
		AnimOption ao2Single = new AnimOption(defaultImgSingle,defaultImgOverSingle, selectedImgSingle, selectedImgOverSingle, "Radio 2");
		AnimOption ao3Single = new AnimOption(defaultImgSingle,defaultImgOverSingle, selectedImgSingle, selectedImgOverSingle, "Radio 3");

		//Step 3: Group the options together
		final ArrayList animListSingle= new ArrayList();
		animListSingle.add(ao1Single);
		animListSingle.add(ao2Single);
		animListSingle.add(ao3Single);
		
		//Step 4: Define the animation settings for the group of options
		final AnimSettings settingsSingle = AnimSettings.getDefault(YuiEasingConstants.EASE_OUT, 0.2, 1, animListSingle);
		AnimGroup animGroupSingle= new AnimGroup("animGroupSingle", settingsSingle);
		add(animGroupSingle);
		
		//Step 5: Apply the settings to all the options in the group
		ListView listViewSingle= new ListView("animViewSingle", animListSingle){
			public void populateItem(ListItem item) {
				AnimOption animOption= (AnimOption) item.getModelObject();
				item.add(new AnimBox("animBoxSingle", item.getIndex(), animOption, settingsSingle));
				item.add(new AnimLabel("animLabelSingle", animOption.getSelectedValue()));
			};
		};
		animGroupSingle.add(listViewSingle);
		
		
		/*
		 * Example 2: Multiple options allowed
		 */
		
		//Step 1: Define the images for the selected, selected mouseover, unselected and unselected mouseover images
		YuiImage selectedImgMultiple = new YuiImage("style/check.jpg");
		YuiImage selectedImgOverMultiple = new YuiImage("style/check.jpg");
		YuiImage defaultImgOverMultiple = new YuiImage("style/uncheck.jpg");
		YuiImage defaultImgMultiple = new YuiImage("style/uncheck-1.jpg");
		
		//Step 2: Define the options for the options, user may specify infinite number of options
		AnimOption ao1Multiple = new AnimOption(defaultImgMultiple,defaultImgOverMultiple, selectedImgMultiple, selectedImgOverMultiple, "Check A");
		AnimOption ao2Multiple = new AnimOption(defaultImgMultiple,defaultImgOverMultiple, selectedImgMultiple, selectedImgOverMultiple, "Check B");
		AnimOption ao3Multiple = new AnimOption(defaultImgMultiple,defaultImgOverMultiple, selectedImgMultiple, selectedImgOverMultiple, "Check C");

		//Step 3: Group the options together
		final ArrayList animListMultiple= new ArrayList();
		animListMultiple.add(ao1Multiple);
		animListMultiple.add(ao2Multiple);
		animListMultiple.add(ao3Multiple);
		
		//Step 4: Define the animation settings for the group of options
		final AnimSettings settingsMultiple = AnimSettings.getDefault(YuiEasingConstants.EASE_OUT, 0.2, 2, animListMultiple);
		AnimGroup animGroupMultiple= new AnimGroup("animGroupMultiple", settingsMultiple);
		add(animGroupMultiple);
		
		//Step 5: Apply the settings to all the options in the group
		ListView listViewMultiple= new ListView("animViewMultiple", animListMultiple){
			protected void populateItem(ListItem item) {
				AnimOption animOption= (AnimOption) item.getModelObject();
				item.add(new AnimBox("animBoxMultiple", item.getIndex(), animOption, settingsMultiple));
				item.add(new AnimLabel("animLabelMultiple", animOption.getSelectedValue()));
			};
		};
		animGroupMultiple.add(listViewMultiple);
		
	}
}
