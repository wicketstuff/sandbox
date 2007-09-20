package wicket.contrib.examples.gmap.manny;

import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.gmap.GMap2;

public class MannyPanel extends Panel
{

	public MannyPanel(String id, String key)
	{
		super(id);
		add(new GMap2("map", key));
	}

}
