package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.dojo.markup.html.percentage.DojoPercentSelector;
import wicket.contrib.dojo.markup.html.percentage.model.PercentageRanges;
import wicket.markup.html.WebPage;
import wicket.model.Model;

public class PercentSelectorSample extends WebPage {
	
	private static PercentageRanges ranges = new PercentageRanges();
	
	public PercentSelectorSample(PageParameters parameters){
		if(ranges.size() == 0){
			ranges.put("value 1", new Integer(25));
			ranges.put("value 2", new Integer(25));
			ranges.put("value 3", new Integer(25));
			ranges.put("value 4", new Integer(15));
			ranges.put("value 5", new Integer(10));
		}
		DojoPercentSelector percent = new DojoPercentSelector(this, "percent", new Model<PercentageRanges>(ranges)); 
		
	}
}
