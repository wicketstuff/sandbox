package wicket.contrib.dojo.markup.html.percentage.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Model to use in PercentSelectorWidget
 * @author <a href="http://www.demay-fr.net/blog/en">Vincent Demay</a>
 *
 */
public class PercentageRanges extends HashMap<String, Integer>
{
	/**
	 * Create a new PencentagesRanges
	 *
	 */
	public PercentageRanges(){
		super();
	}
	
	public void createFromJson(String json){
		String content = json.substring(1, json.length()-1);
		String items[] = content.split(",");
		
		for(int i=0;i<items.length; i++){
			String current = items[i];
			String key = current.substring(1, current.lastIndexOf(':')-1);
			Integer value = new Integer(current.substring(current.lastIndexOf(':')+1, current.length()));
			put(key, value);
		}
	}
	
	public String generateJson(){
		String toReturn = "{";
		
		Iterator<Entry<String, Integer>> ite = this.entrySet().iterator();
		while (ite.hasNext()){
			Entry<String, Integer> current = ite.next();
			toReturn += "\"" + current.getKey() + "\":" + current.getValue() + ",";
		}
		
		return toReturn.substring(0, toReturn.length() -1) + "}";
	}

}
