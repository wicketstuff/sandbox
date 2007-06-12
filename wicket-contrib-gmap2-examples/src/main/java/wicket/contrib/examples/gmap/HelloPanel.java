package wicket.contrib.examples.gmap;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import wicket.contrib.gmap.InfoWindowPanel;

public class HelloPanel extends InfoWindowPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int i;

	public HelloPanel()
	{
		super();
		add(new Label("label", new Model()
		{
		
			@Override
			public Object getObject()
			{
				// TODO Auto-generated method stub
				return Integer.toString(i++);
			}
		
		}));
	}
}
