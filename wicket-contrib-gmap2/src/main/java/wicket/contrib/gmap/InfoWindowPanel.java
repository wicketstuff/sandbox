package wicket.contrib.gmap;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class InfoWindowPanel extends Panel
{

	/**
	 * Default serialVersionUID. 
	 */
	private static final long serialVersionUID = 1L;

	public InfoWindowPanel()
	{
		super("divInfoWindowPanel");
	}

	public InfoWindowPanel(IModel model)
	{
		super("divInfoWindowPanel", model);
	}

}
