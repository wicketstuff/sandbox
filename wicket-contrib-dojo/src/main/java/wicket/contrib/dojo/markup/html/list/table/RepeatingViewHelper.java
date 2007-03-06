package wicket.contrib.dojo.markup.html.list.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.markup.repeater.Item;
import wicket.markup.repeater.RepeatingView;

/**
 * Helper class for RepeatingView that helps to find an Item by index
 */
public class RepeatingViewHelper
{
	/**
	 * find an Item by index
	 */
	public static Item getItemAt(RepeatingView repeater, int pos) {
		Iterator it = repeater.iterator();
		int index=0;
		Item item = null;
		while (index <= pos && it.hasNext()) {
			item = (Item) it.next();
			index++;
		}
		if (index == pos)
			return item;
		throw new IllegalStateException("No item at position " + pos + " in the given RepeatingView");
	}
	
	public static List getListModel(RepeatingView repeater) {
		Iterator it = repeater.iterator();
		List list = new ArrayList();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
}
