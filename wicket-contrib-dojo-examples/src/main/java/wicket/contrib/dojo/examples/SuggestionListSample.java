package wicket.contrib.dojo.examples;

import java.util.Iterator;
import java.util.Map.Entry;

import wicket.PageParameters;
import wicket.contrib.dojo.markup.html.form.suggestionlist.DojoRemoteSuggestionList;
import wicket.contrib.dojo.markup.html.form.suggestionlist.SuggestionList;
import wicket.markup.html.WebPage;

public class SuggestionListSample extends WebPage {
	
	public static SuggestionList allItems = new SuggestionList();

	public SuggestionListSample(PageParameters parameters){
		allItems.put("Alabama", "Alabama");
		allItems.put("Alaska", "Alaska");
		allItems.put("American Samoa", "American Samoa");
		allItems.put("Arizona", "Arizona");
		allItems.put("Arkansas", "Arkansas");
		allItems.put("California", "California");
		allItems.put("Colorado", "Colorado");
		allItems.put("Connecticut", "Connecticut");
		allItems.put("Delaware", "Delaware");
		allItems.put("Columbia", "Columbia");
		allItems.put("Florida", "Florida");
		allItems.put("Georgia", "Georgia");
		allItems.put("Guam", "Guam");
		allItems.put("Hawaii", "Hawaii");
		allItems.put("Idaho", "Idaho");
		allItems.put("Illinois", "Illinois");
		allItems.put("Indiana", "Indiana");
		allItems.put("Iowa", "Iowa");
		allItems.put("Kansas", "Kansas");
		allItems.put("Kentucky", "Kentucky");
		allItems.put("Louisiana", "Louisiana");
		allItems.put("Maine", "Maine");
		allItems.put("Marshall Islands", "Marshall Islands");
		
		
		DojoRemoteSuggestionList list1 = new DojoRemoteSuggestionList(this, "list1"){

			@Override
			public SuggestionList getMatchingValues(String pattern) {
				if (pattern.equals("")) return allItems;
				SuggestionList list = new SuggestionList();
				Iterator<Entry<String, String>> it = allItems.entrySet().iterator();
				while(it.hasNext()){
					Entry<String, String> item = it.next();
					if (item.getValue().toLowerCase().startsWith(pattern.toLowerCase())){
						list.put(item.getKey(), item.getValue());
					}
				}
				return list;
			}
		};
	}
}
