package wicket.contrib.dojo.examples.rssreader;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.model.Model;

/**
 * Simple example class to illustrate usage of DojoAutoUpdateHandler.
 * @author Marco van de Haar
 * @author Ruud Booltink
 *
 */
public class UpdateLabel extends Label

{

	private int count = 0;
	
	/**
	 * construct
	 * @param id Wicket id
	 * @param txt Innitial Label contents
	 */
	public UpdateLabel(MarkupContainer parent, String id, String txt)
	{
		super(parent, id, txt);
		
	}

	/**
	 * This method replaces the innitial label text with a counter which shows <br/>
	 * the amount of times this Label has been updated.
	 * @see wicket.contrib.markup.html.autoupdate.IUpdatable#update()
	 */
	public boolean update()
	{
	
		setModel(new Model("updated " + count + " times"));
		++count;
		return true;
	}
	
	public String getHTMLID()
	{
		return getId();
	}

}
