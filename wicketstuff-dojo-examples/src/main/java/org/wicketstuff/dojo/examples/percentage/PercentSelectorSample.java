package org.wicketstuff.dojo.examples.percentage;

import org.apache.wicket.PageParameters;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.percentage.DojoPercentSelector;
import org.wicketstuff.dojo.markup.html.percentage.model.PercentageRanges;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;

public class PercentSelectorSample extends WicketExamplePage {
	
	private static PercentageRanges ranges = new PercentageRanges();
	
	public PercentSelectorSample(PageParameters parameters){
		if(ranges.size() == 0){
			ranges.put("value 1", new Integer(25));
			ranges.put("value 2", new Integer(25));
			ranges.put("value 3", new Integer(25));
			ranges.put("value 4", new Integer(15));
			ranges.put("value 5", new Integer(10));
		}
		DojoPercentSelector percent = new DojoPercentSelector("percent", new Model(ranges));
		add(percent);
		
	}
}
