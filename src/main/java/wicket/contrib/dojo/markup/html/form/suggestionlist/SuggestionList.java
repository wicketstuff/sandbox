package wicket.contrib.dojo.markup.html.form.suggestionlist;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Model to represent what should be rendered a {@link DojoRemoteSuggestionList}
 * its a HashMap : key is the value given for a label which is the value
 * TODO : more documentation
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class SuggestionList extends HashMap<String, String>{

	/**
	 * Return jsonString to populate suggestionList
	 * @return jSon to populate suggestionList
	 */
	public String getJson()
	{
		String toReturn = "[";
		Iterator<Entry<String, String>> it = this.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> item = it.next();
			toReturn +="[\"" + item.getValue() + "\",\"" + item.getKey() + "\"],";
		}
		return toReturn + "]";
	}
	
}
