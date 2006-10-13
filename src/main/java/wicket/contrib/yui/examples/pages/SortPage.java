package wicket.contrib.yui.examples.pages;

import java.util.ArrayList;

import wicket.contrib.YuiImage;
import wicket.contrib.YuiSortConstants;
import wicket.contrib.markup.html.yui.sort.SortBox;
import wicket.contrib.markup.html.yui.sort.SortGroup;
import wicket.contrib.markup.html.yui.sort.SortSettings;
import wicket.contrib.yui.examples.WicketExamplePage;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

public class SortPage extends WicketExamplePage{
	public SortPage(){	
		
		/**
		 * Need to be able to retrieve the value of the sort list?
		 * how to define the sequence of the value?
		 */
		
		/**
		 * How to add two or more sets?
		 */
		
		/**
		 * How to activate the mode?
		 * The mode should not be a shared variable
		 */
		
		/**
		POINT
		dd1 = new YAHOO.example.DDSwap("1", "A Group");
		dd2 = new YAHOO.example.DDSwap("1", "A Group");
		dd3 = new YAHOO.example.DDSwap("1", "A Group");
		dd4 = new YAHOO.example.DDSwap("1", "A Group");
		
		How to indicate that INTERSECT A and INTERSECT B are not a group?

		INTERSECT A
		dd1 = new YAHOO.example.DDSwap_i("1");
		dd2 = new YAHOO.example.DDSwap_i("1");

		INTERSECT B
		dd3 = new YAHOO.example.DDSwap_i("1");
		dd4 = new YAHOO.example.DDSwap_i("1");

		Need to group myself??
		*/

		
		/**
		 * Should the top and left position of the image be defined this way?
		 */
		YuiImage blue = new YuiImage("style/blue.bmp"); 
		blue.setSortTop(150);
		blue.setSortLeft(80);
		
		YuiImage green = new YuiImage("style/green.bmp"); 
		green.setSortTop(150);
		green.setSortLeft(195);
		
		YuiImage pink = new YuiImage("style/pink.bmp"); 
		pink.setSortTop(150);
		pink.setSortLeft(305);
		
		YuiImage yellow = new YuiImage("style/yellow.bmp"); 
		yellow.setSortTop(150);
		yellow.setSortLeft(415);

		final ArrayList sortList= new ArrayList();
		sortList.add(blue);
		sortList.add(green);
		sortList.add(pink);
		sortList.add(yellow);
		
		final SortSettings settings = SortSettings.getDefault(YuiSortConstants.INTERSECT, sortList);
		SortGroup sortGroup= new SortGroup("sortGroup", settings);
		add(sortGroup);
	
		ListView sortListView= new ListView("sortListView", sortList){
			protected void populateItem(ListItem item) {
				YuiImage image= (YuiImage) item.getModelObject();
				item.add(new SortBox("sortBox", item.getIndex(), image, settings));
			};
		};
		sortGroup.add(sortListView);
	}	
}
