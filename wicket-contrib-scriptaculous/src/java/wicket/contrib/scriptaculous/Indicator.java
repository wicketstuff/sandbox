package wicket.contrib.scriptaculous;

import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.markup.html.panel.Panel;

public class Indicator extends Panel {

	public Indicator() {
		super("indicator");

		add(new Image("indicatorImage", "indicator.gif"));
		add(new Label("indicatorLabel", "Processing..."));
	}

}
