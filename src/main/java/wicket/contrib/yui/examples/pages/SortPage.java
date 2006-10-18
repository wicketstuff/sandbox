package wicket.contrib.yui.examples.pages;

import java.util.ArrayList;
import java.util.List;

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
		 * Example A: Intersect Mode
		 */
		
		YuiImage blueA = new YuiImage("style/blue.bmp", "blue");  
		blueA.setTop(150);
		blueA.setLeft(80);
		
		YuiImage greenA = new YuiImage("style/green.bmp", "green"); 
		greenA.setTop(150);
		greenA.setLeft(195);
		
		YuiImage pinkA = new YuiImage("style/pink.bmp", "pink"); 
		pinkA.setTop(150);
		pinkA.setLeft(305);
		
		YuiImage yellowA = new YuiImage("style/yellow.bmp", "yellow"); 
		yellowA.setTop(150);
		yellowA.setLeft(415);

		final List<YuiImage> sortListA= new ArrayList<YuiImage>();
		sortListA.add(blueA);
		sortListA.add(greenA);
		sortListA.add(pinkA);
		sortListA.add(yellowA);
		
		final SortSettings settingsA = SortSettings.getDefault(YuiSortConstants.INTERSECT, sortListA);
		SortGroup sortGroupA= new SortGroup("sortGroupA", settingsA);
		add(sortGroupA);
	
		ListView sortListViewA= new ListView("sortListViewA", sortListA){
			protected void populateItem(ListItem item) {
				YuiImage imageA= (YuiImage) item.getModelObject();
				item.add(new SortBox("sortBoxA", item.getIndex(), imageA, settingsA));
			};
		};
		sortGroupA.add(sortListViewA);
		
		
		/**
		 * Example B: Point Mode
		 */
		
		YuiImage blueB = new YuiImage("style/blue.bmp", "blue"); 
		blueB.setTop(300);
		blueB.setLeft(80);
		
		YuiImage greenB = new YuiImage("style/green.bmp", "green"); 
		greenB.setTop(300);
		greenB.setLeft(195);
		
		YuiImage pinkB = new YuiImage("style/pink.bmp", "pink"); 
		pinkB.setTop(300);
		pinkB.setLeft(305);
		
		YuiImage yellowB = new YuiImage("style/yellow.bmp", "yellow"); 
		yellowB.setTop(300);
		yellowB.setLeft(415);

		final List<YuiImage> sortListB= new ArrayList<YuiImage>();
		sortListB.add(blueB);
		sortListB.add(greenB);
		sortListB.add(pinkB);
		sortListB.add(yellowB);
		
		final SortSettings settingsB = SortSettings.getDefault(YuiSortConstants.POINT, sortListB);
		SortGroup sortGroupB= new SortGroup("sortGroupB", settingsB);
		add(sortGroupB);
	
		ListView sortListViewB= new ListView("sortListViewB", sortListB){
			protected void populateItem(ListItem item) {
				YuiImage imageB= (YuiImage) item.getModelObject();
				item.add(new SortBox("sortBoxB", item.getIndex(), imageB, settingsB));
			};
		};
		sortGroupB.add(sortListViewB);
	}	
}
